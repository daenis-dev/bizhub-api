package it.events;

import com.greenpalmsolutions.security.events.api.behavior.UpdateEvent;
import com.greenpalmsolutions.security.events.api.model.EventDetails;
import com.greenpalmsolutions.security.events.api.model.UpdateEventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateEventIT {

    @Autowired
    private UpdateEvent updateEvent;

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

    @Test
    void updatesTheEvent() {
        EventDetails eventDetails = updateEvent.updateEventForRequest(new UpdateEventRequest()
                .withEventId("1")
                .withName("Meeting One")
                .withStartDateTime(ZonedDateTime.now())
                .withEndDateTime(ZonedDateTime.now().plusHours(2)));

        assertThat(eventDetails.getId()).isEqualTo(1);
    }
}
