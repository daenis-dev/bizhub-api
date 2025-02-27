package it.schedulekeys;

import com.greenpalmsolutions.security.schedulekeys.api.behavior.CreateScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreateScheduleKeyIT {

    @Autowired
    private CreateScheduleKey createScheduleKey;

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

    @Transactional
    @Modifying
    @Test
    void createsScheduleKey() {
        ScheduleKeyDetails theScheduleKeyDetails = createScheduleKey.createScheduleKeyForCurrentUser();

        assertThat(theScheduleKeyDetails.getId()).isGreaterThan(0);
        assertThat(theScheduleKeyDetails.getToken()).isNotEmpty();
    }

    @AfterEach
    void cleanUp() {
        entityManager.createQuery("DELETE FROM ScheduleKey sk WHERE sk.userId = :userId")
                .setParameter("userId", "123-abc")
                .executeUpdate();
    }
}
