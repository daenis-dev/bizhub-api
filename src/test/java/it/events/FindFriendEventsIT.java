package it.events;

import com.greenpalmsolutions.security.events.api.behavior.FindFriendEvents;
import com.greenpalmsolutions.security.events.api.model.FriendEventDetails;
import com.greenpalmsolutions.security.events.api.model.FindFriendEventsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindFriendEventsIT {

    @Autowired
    private FindFriendEvents findFriendEvents;

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
    void findsTheFriendEventsForTheUsername() {
        List<FriendEventDetails> friendEventDetails = findFriendEvents.findFriendEventsForRequest(
                new FindFriendEventsRequest().withUsername("someone@mail.com"));

        assertThat(friendEventDetails.get(0).getStartDateTime()).isNotNull();
        assertThat(friendEventDetails.get(0).getEndDateTime()).isNotNull();
    }
}
