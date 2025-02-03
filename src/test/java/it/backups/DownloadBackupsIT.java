package it.backups;

import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackups;
import com.greenpalmsolutions.security.backups.api.model.DownloadBackupsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DownloadBackupsIT {

    @Autowired
    private DownloadBackups downloadBackups;

    @BeforeEach
    void init() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        Jwt jwt = Mockito.mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        when(jwt.getSubject()).thenReturn("123-abc");
    }

    @Test
    void downloadsTheBackups() {
        byte[] theBackups = downloadBackups.downloadBackupsForRequest(
                new DownloadBackupsRequest().addFromListOfFileNamesAsString("test.txt"));

        assertThat(theBackups).isNotEmpty();
        assertThat(theBackups).hasSizeGreaterThan(10);
    }
}
