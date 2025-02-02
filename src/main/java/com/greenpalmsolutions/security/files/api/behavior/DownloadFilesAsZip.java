package com.greenpalmsolutions.security.files.api.behavior;

import java.util.List;

public interface DownloadFilesAsZip {

    byte[] downloadFilesForFilePaths(List<String> filePaths);
}
