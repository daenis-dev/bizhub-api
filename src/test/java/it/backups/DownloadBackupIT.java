package it.backups;

import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackup;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.DownloadBackupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DownloadBackupIT {

    @Value("${app.file-storage.local.location}")
    private String FILE_STORAGE_LOCATION;

    @Autowired
    private DownloadBackup downloadBackup;

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

    @Test
    void downloadsTheBackup() throws Exception {
        byte[] thePredictedContent = Files.readAllBytes(Paths.get(FILE_STORAGE_LOCATION + "/test.txt"));

        BackupDetails theBackupDetails = downloadBackup.downloadForRequest(new DownloadBackupRequest().withFileName("test.txt"));

        assertThat(theBackupDetails.getFileName()).isEqualTo("test.txt");
        assertThat(theBackupDetails.getFileContent()).isEqualTo(thePredictedContent);
    }
}
