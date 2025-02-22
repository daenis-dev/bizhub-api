package it.events;

import com.greenpalmsolutions.security.events.api.behavior.CreateEvent;
import com.greenpalmsolutions.security.events.api.model.CreateEventRequest;
import com.greenpalmsolutions.security.events.api.model.EventDetails;
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

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateEventIT {

    @Autowired
    private CreateEvent createEvent;

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
    void createsTheEvent() {
        EventDetails theDetails = createEvent.createEventForRequest(new CreateEventRequest()
                .withName("Meeting One")
                .withStartDateTime(ZonedDateTime.now())
                .withEndDateTime(ZonedDateTime.now().plusHours(1)));

        assertThat(theDetails.getId()).isGreaterThan(0);
        assertThat(theDetails.getName()).isEqualTo("Meeting One");
    }

    @AfterEach
    void cleanUp() {
        entityManager.createQuery("DELETE FROM Event e WHERE e.userId = :userId")
                .setParameter("userId", "123-abc")
                .executeUpdate();
    }
}
