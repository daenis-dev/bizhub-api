package it.files;

import com.greenpalmsolutions.security.files.api.behavior.DownloadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DownloadsFileIT {

    @Value("${app.file-storage.local.location}")
    private String FILE_STORAGE_LOCATION;

    @Autowired
    private DownloadFile downloadFile;

    // TODO: failing test
    @Test
    void downloadsTheFile() throws Exception {
        byte[] thePredictedFile = Files.readAllBytes(Path.of(getClass().getClassLoader()
                .getResource("storage/test.txt").toURI()));

        byte[] theDownloadedFile = downloadFile.downloadFileWithFilePath(FILE_STORAGE_LOCATION + "/test.zip");

        assertThat(theDownloadedFile).isEqualTo(thePredictedFile);
    }
}
