package com.greenpalmsolutions.security.files.api.behavior;

import java.util.List;

public interface DownloadFiles {

    byte[] downloadZipForFilePaths(List<String> filePaths);
}
