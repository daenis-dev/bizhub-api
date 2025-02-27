package it.schedulekeys;

import com.greenpalmsolutions.security.schedulekeys.api.behavior.DisableScheduleKey;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.mockito.Mockito.when;

@SpringBootTest
class DisableScheduleKeyIT {

    @Autowired
    private DisableScheduleKey disableScheduleKey;

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

        when(jwt.getSubject()).thenReturn("456-def");
    }

    @Transactional
    @Modifying
    @Test
    void disablesTheScheduleKeyForTheCurrentUser() {
        disableScheduleKey.disableActiveScheduleKeyForUser();
    }

    @AfterEach
    void cleanUp() {
        entityManager.createQuery("UPDATE ScheduleKey sk SET sk.isActive = true WHERE sk.userId = :userId")
                .setParameter("userId", "456-def")
                .executeUpdate();
    }
}
