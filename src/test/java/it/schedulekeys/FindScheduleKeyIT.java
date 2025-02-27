package it.schedulekeys;

import com.greenpalmsolutions.security.schedulekeys.api.behavior.FindScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
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
class FindScheduleKeyIT {

    @Autowired
    private FindScheduleKey findScheduleKey;

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
    void findsTheScheduleKeyForTheCurrentUser() {
        ScheduleKeyDetails theScheduleKeyDetails = findScheduleKey.findScheduleKeyForCurrentUser();

        assertThat(theScheduleKeyDetails.getId()).isGreaterThan(0);
        assertThat(theScheduleKeyDetails.getToken()).isEqualTo("xyz");
    }
}
