package com.greenpalmsolutions.security.backups.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BackupTest {

    private Backup backup;

    @BeforeEach
    void init() {
        backup = new Backup();
    }

    @Test
    void getsTheOriginalFileName() {
        String theFilePath = "/some/file.zip";
        String theFileExtension = "exe";
        backup.setFilePath(theFilePath);
        backup.setFileExtension(theFileExtension);

        String theOriginalFileName = backup.getOriginalFileName();

        assertThat(theOriginalFileName).isEqualTo("file.exe");
    }
}