package com.greenpalmsolutions.security.backups.api.behavior;

import com.greenpalmsolutions.security.backups.api.model.DownloadBackupsRequest;

public interface DownloadBackups {

    byte[] downloadBackupsForRequest(DownloadBackupsRequest request);
}
