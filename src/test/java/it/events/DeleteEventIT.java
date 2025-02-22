package it.events;

import com.greenpalmsolutions.security.events.api.behavior.DeleteEvent;
import com.greenpalmsolutions.security.events.api.model.DeleteEventRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.mockito.Mockito.when;

@SpringBootTest
public class DeleteEventIT {

    private long generatedId;

    @Autowired
    private DeleteEvent deleteEvent;

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

        this.generatedId = ((Number) entityManager.createNativeQuery(
                "INSERT INTO events (name, user_id, start_date_time_in_utc, end_date_time_in_utc, created_date_time_in_utc, modified_date_time_in_utc) " +
                        "VALUES ('Meeting Three', '123-abc', NOW(), NOW(), NOW(), NOW()) RETURNING id"
        ).getSingleResult()).longValue();
    }

    @Test
    void deletesTheEvent() {
        deleteEvent.deleteEventForRequest(new DeleteEventRequest().withEventId(String.valueOf(generatedId)));
    }
}
