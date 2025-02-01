package com.greenpalmsolutions.security.backups.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DownloadBackupRequestTest {

    private DownloadBackupRequest downloadBackupRequest;

    @BeforeEach
    void init() {
        downloadBackupRequest = new DownloadBackupRequest();
    }

    @Test
    void setsFileNameForValidInput() {
        String theFileName = "some-file.zip";

        DownloadBackupRequest theUpdatedRequest = downloadBackupRequest.withFileName(theFileName);

        String theFileNameFromTheUpdatedRequest = theUpdatedRequest.getFileName();
        assertThat(theFileNameFromTheUpdatedRequest).isEqualTo(theFileName);
    }

    @Test
    void doesNotSetTheFileNameForNullInput() {
        String thePredictedMessage = "File name is required to download backup";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> downloadBackupRequest.withFileName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFileNameForEmptyInput() {
        String thePredictedMessage = "File name is required to download backup";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> downloadBackupRequest.withFileName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void getsTheFileNameWithoutTheExtension() {
        downloadBackupRequest.withFileName("something.txt");

        String theFileNameWithoutTheExtension = downloadBackupRequest.getFileNameWithoutExtension();

        assertThat(theFileNameWithoutTheExtension).isEqualTo("something");
    }
}