package it.events;

import com.greenpalmsolutions.security.events.api.behavior.FindSchedule;
import com.greenpalmsolutions.security.events.api.model.FindScheduleRequest;
import com.greenpalmsolutions.security.events.api.model.ScheduleDetails;
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
public class FindScheduleIT {

    @Autowired
    private FindSchedule findSchedule;

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
    void findsTheSchedule() {
        ScheduleDetails theScheduleDetails = findSchedule.findScheduleForRequest(new FindScheduleRequest()
                .withUsername("someone@mail.com").withScheduleKey("xyz"));

        assertThat(theScheduleDetails.getUsername()).isEqualTo("someone@mail.com");
        assertThat(theScheduleDetails.getEventDateDetails().size()).isEqualTo(2);
    }
}
