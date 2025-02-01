package com.greenpalmsolutions.security.backups.internal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "backups")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
class Backup {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "backups_seq")
    @SequenceGenerator(name = "backups_seq", sequenceName = "backups_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_compressed")
    private boolean isCompressed;

    public String getOriginalFileName() {
        return getFileNameWithoutExtension(getFileNameFromFullPath(filePath))
                + '.' + fileExtension;
    }

    private String getFileNameFromFullPath(String fullPath) {
        return fullPath.replaceAll(".*/", "");
    }

    private String getFileNameWithoutExtension(String fileName) {
        return fileName.replaceAll("\\.[^.]+$", "");
    }
}
