package com.greenpalmsolutions.security.files.api.behavior;

import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;

public interface UploadFile {

    void uploadFileForRequest(UploadFileRequest request);
}
