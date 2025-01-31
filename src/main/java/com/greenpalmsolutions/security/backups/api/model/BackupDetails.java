package com.greenpalmsolutions.security.backups.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BackupDetails {

    private String fileName;
    private byte[] fileContent;
}
