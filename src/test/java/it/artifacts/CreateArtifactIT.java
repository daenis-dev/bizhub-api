package it.artifacts;

import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifact;
import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactResponse;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class CreateArtifactIT {

    @Autowired
    private CreateArtifact createArtifact;

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
        String theName = "File 4";
        String theFilePath = "C:/some/file4.txt";
        String theHash = "456789";

        CreateArtifactResponse theResponse = createArtifact.createArtifactForRequest(new CreateArtifactRequest()
                .withName(theName)
                .withFilePath(theFilePath)
                .withHash(theHash));

        assertThat(theResponse.getId()).isGreaterThan(0L);
        assertThat(theResponse.getName()).isEqualTo(theName);
        assertThat(theResponse.getFilePath()).isEqualTo(theFilePath);
    }

    @AfterEach
    void deleteArtifactForTest() {
        entityManager.createNativeQuery(
                "SELECT setval('artifacts_id_seq', (SELECT COALESCE(MAX(id), 1) FROM artifacts) - 1)")
                .getSingleResult();
    }
}
