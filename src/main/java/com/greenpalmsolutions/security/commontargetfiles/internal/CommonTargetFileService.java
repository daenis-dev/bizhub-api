package com.greenpalmsolutions.security.commontargetfiles.internal;

import com.greenpalmsolutions.security.commontargetfiles.api.behavior.FindCommonTargetFilePaths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class CommonTargetFileService implements FindCommonTargetFilePaths {

    private final CommonTargetFileRepository commonTargetFileRepository;

    @Override
    public List<String> findForWindows() {
        return commonTargetFileRepository.findAll().stream().map(CommonTargetFile::getFilePath).toList();
    }
}
