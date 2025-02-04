package com.greenpalmsolutions.security.backups.api.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UploadBackupsResponseTest {

    private UploadBackupsResponse uploadBackupsResponse;

    @Test
    void getsTheOccupiedUserStorageInBytes() {
        uploadBackupsResponse = new UploadBackupsResponse(100);

        String theFormattedSize = uploadBackupsResponse.getOccupiedUserStorageInBytes();

        assertThat(theFormattedSize).isEqualTo("100 bytes");
    }

    @Test
    void getsTheOccupiedUserStorageInBytesForKilobytes() {
        uploadBackupsResponse = new UploadBackupsResponse(3000L);

        String theFormattedSize = uploadBackupsResponse.getOccupiedUserStorageInBytes();

        assertThat(theFormattedSize).isEqualTo("3.0 KB");
    }

    @Test
    void getsTheOccupiedUserStorageInBytesForMegabytes() {
        uploadBackupsResponse = new UploadBackupsResponse(4000000L);

        String theFormattedSize = uploadBackupsResponse.getOccupiedUserStorageInBytes();

        assertThat(theFormattedSize).isEqualTo("4.0 MB");
    }

    @Test
    void getsTheOccupiedUserStorageInBytesForGigabytes() {
        uploadBackupsResponse = new UploadBackupsResponse(7000000000L);

        String theFormattedSize = uploadBackupsResponse.getOccupiedUserStorageInBytes();

        assertThat(theFormattedSize).isEqualTo("7.0 GB");
    }
}