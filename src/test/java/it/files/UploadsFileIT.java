package it.files;

import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UploadsFileIT {

    @Value("${app.file-storage.location}")
    private String FILE_STORAGE_LOCATION;

    @Autowired
    private UploadFile uploadFile;

    @Test
    void uploadsTheFile() {
        uploadFile.uploadFileForRequest(new UploadFileRequest()
                .withFilePath(FILE_STORAGE_LOCATION + "/test2.zip")
                .withFileContents("Howdy!".getBytes()));

        File theUploadedFile = new File(FILE_STORAGE_LOCATION + "/test2.zip");
        assertThat(theUploadedFile).isNotEmpty();
    }

    @AfterEach
    void cleanUp() throws Exception {
        Files.delete(Paths.get(FILE_STORAGE_LOCATION + "/test2.zip"));
    }
}
