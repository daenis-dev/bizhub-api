package it.artifacts;

import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifact;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidateArtifactIT {

    @Autowired
    private ValidateArtifact validateArtifact;

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
    void validatesThatTheArtifactHasNotChanged() {
        ValidateArtifactResponse theResponse = validateArtifact.validateArtifactForRequest(new ValidateArtifactRequest()
                .withId("1")
                .withHash("1234567890abcde1"));

        assertThat(theResponse.isValid()).isTrue();
        assertThat(theResponse.getMessage()).isEqualTo("Artifact is valid");
    }

    @Test
    void validatesThatTheArtifactHasChanged() {
        ValidateArtifactResponse theResponse = validateArtifact.validateArtifactForRequest(new ValidateArtifactRequest()
                .withId("1")
                .withHash("1234567890abcde2"));

        assertThat(theResponse.isValid()).isFalse();
        assertThat(theResponse.getMessage()).isEqualTo("Artifact is no longer valid; hash has been updated");
    }
}
