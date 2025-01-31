package it.artifacts;

import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.List;

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
        ArtifactRequest requestOne = new ArtifactRequest();
        requestOne.setFilePath("C:/Windows/System32/svchost.exe");
        requestOne.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1");
        ArtifactRequest requestTwo = new ArtifactRequest();
        requestTwo.setFilePath("C:/Windows/System32/lsass.exe");
        requestTwo.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f4");
        ArtifactRequest requestThree = new ArtifactRequest();
        requestThree.setFilePath("C:/Windows/System32/netsh.exe");
        requestThree.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f3");

        ValidateArtifactsResponse theResponse = validateArtifacts.validateArtifactsForRequests(
                Arrays.asList(requestOne, requestTwo, requestThree));

        assertThat(theResponse.getCorruptArtifactFilePaths()).contains("C:/Windows/System32/lsass.exe");
    }
}
