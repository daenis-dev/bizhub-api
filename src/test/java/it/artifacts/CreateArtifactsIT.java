package it.artifacts;

import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class CreateArtifactsIT {

    @Autowired
    private CreateArtifacts createArtifacts;

    @Autowired
    private EntityManager entityManager;

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
    @Transactional
    void createsArtifact() {
        ArtifactRequest request = new ArtifactRequest();
        request.setFilePath("C:/some/file4.txt");
        request.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1");

        createArtifacts.createArtifactForRequest(Collections.singletonList(request));
    }

    @AfterEach
    void deleteArtifactForTest() {
        entityManager.createNativeQuery(
                "SELECT setval('artifacts_id_seq', (SELECT COALESCE(MAX(id), 1) FROM artifacts) - 1)")
                .getSingleResult();
    }
}
