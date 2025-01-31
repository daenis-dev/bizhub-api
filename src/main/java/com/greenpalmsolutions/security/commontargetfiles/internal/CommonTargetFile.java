package com.greenpalmsolutions.security.commontargetfiles.internal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "common_target_files")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
class CommonTargetFile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_target_files_seq")
    @SequenceGenerator(name = "common_target_files_seq", sequenceName = "common_target_files_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "file_path")
    private String filePath;
}
