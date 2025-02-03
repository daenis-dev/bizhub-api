package it.backups;

import com.greenpalmsolutions.security.backups.api.behavior.UploadBackups;
import com.greenpalmsolutions.security.backups.api.model.UploadBackupsRequest;
import com.greenpalmsolutions.security.backups.api.model.UploadBackupsResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UploadBackupsIT {

    @Value("${app.file-storage.local.location}")
    private String FILE_STORAGE_LOCATION;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UploadBackups uploadBackups;

    @BeforeEach
    void init() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        Jwt jwt = Mockito.mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        when(jwt.getSubject()).thenReturn("123-abc");
    }

    @Transactional
    @Modifying
    @Test
    void uploadsBackups() {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "file1.txt", "text/plain", "Hello, World!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile(
                "files", "file2.txt", "text/plain", "Another file.".getBytes());
        MultipartFile[] files = new MultipartFile[]{ file1, file2 };
        UploadBackupsRequest request = new UploadBackupsRequest().addFromFiles(files);

        UploadBackupsResponse theResponse = uploadBackups.uploadBackupsForRequest(request);

        boolean theFilesExist = Files.exists(Paths.get(FILE_STORAGE_LOCATION + "/123-abc/file1.zip"))
                && Files.exists(Paths.get(FILE_STORAGE_LOCATION + "/123-abc/file2.zip"));
        assertThat(theFilesExist).isTrue();
        assertThat(theResponse.getFormattedSize()).isEqualTo("48 bytes");
    }

    @Transactional
    @Modifying
    @Test
    void throwsAnExceptionIfTheUserExceedsTenGigabytesOfStorage() {
        entityManager.createNativeQuery(
                "INSERT INTO backups (file_path, uncompressed_file_size_in_bytes, file_extension, user_id) VALUES ("
                        + "'src/test/resources/storage/123-abc/big.zip', 9999999977, 'txt', '123-abc')"
        ).executeUpdate();
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "file1.txt", "text/plain", "Hello, World!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile(
                "files", "file2.txt", "text/plain", "Another file.".getBytes());
        MultipartFile[] files = new MultipartFile[]{ file1, file2 };
        UploadBackupsRequest request = new UploadBackupsRequest().addFromFiles(files);

        RuntimeException theException = assertThrows(RuntimeException.class,
                () -> uploadBackups.uploadBackupsForRequest(request));

        assertThat(theException.getMessage()).isEqualTo(
                "Maximum storage space of 10GB cannot be surpassed for the user");
    }

    @AfterEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(Paths.get(FILE_STORAGE_LOCATION + "/123-abc/file1.zip"));
        Files.deleteIfExists(Paths.get(FILE_STORAGE_LOCATION + "/123-abc/file2.zip"));

        entityManager.createQuery("DELETE FROM Backup b WHERE "
                        + "(b.userId = :userId AND (b.filePath = :filePathOne OR b.filePath = :filePathTwo)) OR "
                        + "(b.userId = :userId AND b.filePath = :filePathThree)")
                .setParameter("userId", "123-abc")
                .setParameter("filePathOne", "src/test/resources/storage/123-abc/file1.zip")
                .setParameter("filePathTwo", "src/test/resources/storage/123-abc/file2.zip")
                .setParameter("filePathThree", "src/test/resources/storage/123-abc/big.zip")
                .executeUpdate();
    }
}
