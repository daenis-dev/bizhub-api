package com.greenpalmsolutions.security.files.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UploadFileRequestTest {

    private UploadFileRequest uploadFileRequest;

    @BeforeEach
    void init() {
        uploadFileRequest = new UploadFileRequest();
    }

    @Test
    void setsFilePathForValidInput() {
        String theFilePath = "/storage/some-file.txt";

        UploadFileRequest theUpdatedRequest = uploadFileRequest.withFilePath(theFilePath);

        String theFilePathFromTheUpdatedRequest = theUpdatedRequest.getFilePath();
        assertThat(theFilePathFromTheUpdatedRequest).isEqualTo(theFilePath);
    }

    @Test
    void doesNotSetTheFilePathForNullInput() {
        String thePredictedMessage = "File path is required to upload file";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadFileRequest.withFilePath(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFilePathForEmptyInput() {
        String thePredictedMessage = "File path is required to upload file";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadFileRequest.withFilePath(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsFileContentsForValidInput() {
        byte[] theFileContents = "Hello".getBytes();

        UploadFileRequest theUpdatedRequest = uploadFileRequest.withFileContents(theFileContents);

        byte[] theFileContentsFromTheUpdatedRequest = theUpdatedRequest.getFileContents();
        assertThat(theFileContentsFromTheUpdatedRequest).isEqualTo(theFileContents);
    }

    @Test
    void doesNotSetTheFileContentsForNullInput() {
        String thePredictedMessage = "File contents are required to upload file";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadFileRequest.withFileContents(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFileContentsForEmptyInput() {
        String thePredictedMessage = "File contents are required to upload file";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadFileRequest.withFileContents(new byte[0]));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}