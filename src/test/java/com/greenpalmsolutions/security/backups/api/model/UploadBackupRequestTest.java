package com.greenpalmsolutions.security.backups.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UploadBackupRequestTest {

    private UploadBackupRequest uploadBackupRequest;

    @BeforeEach
    void init() {
        uploadBackupRequest = new UploadBackupRequest();
    }

    @Test
    void setsFileNameForValidInput() {
        String theFileName = "some-file.txt";

        UploadBackupRequest theUpdatedRequest = uploadBackupRequest.withFileName(theFileName);

        String theFileNameFromTheUpdatedRequest = theUpdatedRequest.getFileName();
        assertThat(theFileNameFromTheUpdatedRequest).isEqualTo(theFileName);
    }

    @Test
    void doesNotSetTheFileNameForNullInput() {
        String thePredictedMessage = "File name is required to create backup";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadBackupRequest.withFileName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFileNameForEmptyInput() {
        String thePredictedMessage = "File name is required to create backup";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadBackupRequest.withFileName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsFileContentsForValidInput() {
        byte[] theFileContents = "Hello".getBytes();

        UploadBackupRequest theUpdatedRequest = uploadBackupRequest.withFileContents(theFileContents);

        byte[] theFileContentsFromTheUpdatedRequest = theUpdatedRequest.getFileContents();
        assertThat(theFileContentsFromTheUpdatedRequest).isEqualTo(theFileContents);
    }

    @Test
    void doesNotSetTheFileContentsForNullInput() {
        String thePredictedMessage = "File contents are required to create backup";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadBackupRequest.withFileContents(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFileContentsForEmptyInput() {
        String thePredictedMessage = "File contents are required to create backup";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadBackupRequest.withFileContents(new byte[0]));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void getsTheContentLengthInBytes() {
        uploadBackupRequest.withFileContents("Hello".getBytes());
        int thePredictedContentLengthInBytes = "Hello".getBytes().length;

        int theContentLengthInBytes = uploadBackupRequest.getContentLengthInBytes();


        assertThat(theContentLengthInBytes).isEqualTo(thePredictedContentLengthInBytes);
    }

    @Test
    void getsTheFileExtension() {
        uploadBackupRequest.withFileName("something.txt");

        String theFileExtension = uploadBackupRequest.getFileExtension();

        assertThat(theFileExtension).isEqualTo("txt");
    }

    @Test
    void getsABlankStringIfThereIsNoFileExtension() {
        uploadBackupRequest.withFileName("/some/directory");

        String theFileExtension = uploadBackupRequest.getFileExtension();

        assertThat(theFileExtension).isBlank();
    }

    @Test
    void getsTheFileNameWithoutTheExtension() {
        uploadBackupRequest.withFileName("something.txt");

        String theFileNameWithoutTheExtension = uploadBackupRequest.getFileNameWithoutExtension();

        assertThat(theFileNameWithoutTheExtension).isEqualTo("something");
    }

    @Test
    void returnsTheFileNameIfThereIsNoFileExtension() {
        uploadBackupRequest.withFileName("no-name-file");

        String theFileNameWithoutTheExtension = uploadBackupRequest.getFileNameWithoutExtension();

        assertThat(theFileNameWithoutTheExtension).isEqualTo("no-name-file");
    }
}