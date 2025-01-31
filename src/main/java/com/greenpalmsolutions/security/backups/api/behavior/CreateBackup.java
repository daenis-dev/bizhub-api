package com.greenpalmsolutions.security.backups.api.behavior;

import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.BackupRequest;
import com.greenpalmsolutions.security.backups.api.model.BackupRequests;

public interface CreateBackup {

    BackupDetails createBackupForRequests(BackupRequests requests);
}
