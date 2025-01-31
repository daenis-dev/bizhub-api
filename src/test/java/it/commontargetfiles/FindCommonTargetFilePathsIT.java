package it.commontargetfiles;

import com.greenpalmsolutions.security.commontargetfiles.api.behavior.FindCommonTargetFilePaths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FindCommonTargetFilePathsIT {

    @Autowired
    private FindCommonTargetFilePaths findCommonTargetFilePaths;

    @Test
    void findsCommonTargetFilePaths() {
        List<String> theReturnedFilePaths = findCommonTargetFilePaths.findForWindows();

        assertThat(theReturnedFilePaths.size()).isEqualTo(50);
        assertThat(theReturnedFilePaths.contains("C:/Windows/System32/svchost.exe"));
    }
}
