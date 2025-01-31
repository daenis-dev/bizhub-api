package com.greenpalmsolutions.security.commontargetfiles.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommonTargetFileController {

    private final FindCommonTargetFilePaths findCommonTargetFilePaths;

    @GetMapping("/v1/common-target-file-paths/windows")
    public ResponseEntity<List<String>> findCommonTargetFilePathsForWindows() {
        return ResponseEntity.ok(findCommonTargetFilePaths.findForWindows());
    }
}
