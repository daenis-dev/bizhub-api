package it.artifacts;

import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
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
public class ValidateArtifactsIT {

    @Autowired
    private ValidateArtifacts validateArtifacts;

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
    void returnsAFilePathIfTheFileHasBeenUpdated() {
        ValidateArtifactsResponse theResponse = validateArtifacts.validateArtifactsForRequest(
                new ValidateArtifactsRequest().withArtifactHashes(
                        "C:/some/file1.txt:1234567890abcde1,C:/some/file2.txt:1234567890abcde4,C:/some/file3.txt:1234567890abcde3"));

        assertThat(theResponse.getCorruptArtifactFilePaths()).contains("C:/some/file2.txt");
    }
}
