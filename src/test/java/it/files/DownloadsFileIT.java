package it.files;

import com.greenpalmsolutions.security.files.api.behavior.DownloadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DownloadsFileIT {

    @Value("${app.file-storage.local.location}")
    private String FILE_STORAGE_LOCATION;

    @Autowired
    private DownloadFile downloadFile;

    @Test
    void downloadsTheFile() {
        byte[] thePredictedFileContents = new byte[]{72, 101, 108, 108, 111, 33};

        byte[] theDownloadedFile = downloadFile.downloadFileWithFilePath(FILE_STORAGE_LOCATION + "/123-abc/test.zip");

        assertThat(theDownloadedFile).isEqualTo(thePredictedFileContents);
    }

    @Test
    void throwsExceptionIfFileDoesNotExist() {
        RuntimeException theException = assertThrows(RuntimeException.class,
                () -> downloadFile.downloadFileWithFilePath(FILE_STORAGE_LOCATION + "/does-not-exist"));

        String theMessage = theException.getMessage();
        assertThat(theMessage).isEqualTo("Error while downloading file");
    }
}
