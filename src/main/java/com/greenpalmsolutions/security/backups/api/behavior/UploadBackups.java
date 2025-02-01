package com.greenpalmsolutions.security.backups.api.behavior;

import com.greenpalmsolutions.security.backups.api.model.UploadBackupsResponse;
import com.greenpalmsolutions.security.backups.api.model.UploadBackupsRequest;

public interface UploadBackups {

    UploadBackupsResponse uploadBackupsForRequest(UploadBackupsRequest request);
}
