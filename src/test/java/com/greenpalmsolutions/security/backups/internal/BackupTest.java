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

    @Test
    void isEqualToABackupWithTheSameProperties() {
        long theId = 2;
        String theFilePath = "/some/file.zip";
        String theFileExtension = "exe";
        String theUserId = "123-abc";
        backup.setId(theId);
        backup.setFilePath(theFilePath);
        backup.setFileExtension(theFileExtension);
        backup.setUserId(theUserId);
        Backup theOtherBackup = new Backup();
        theOtherBackup.setId(theId);
        theOtherBackup.setFilePath(theFilePath);
        theOtherBackup.setFileExtension(theFileExtension);
        theOtherBackup.setUserId(theUserId);

        boolean theBackupEqualsTheOtherBackup = backup.equals(theOtherBackup);

        assertThat(theBackupEqualsTheOtherBackup).isEqualTo(true);
    }

    @Test
    void generatesHashCode() {
        backup.setId(2);
        backup.setFilePath("/some/file.zip");
        backup.setFileExtension("txt");
        backup.setUserId("123-abc");

        int theHashCode = backup.hashCode();

        assertThat(theHashCode).isEqualTo(-2072297039);
    }
}