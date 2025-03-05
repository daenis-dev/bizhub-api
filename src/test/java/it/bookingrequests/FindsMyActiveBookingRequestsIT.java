package it.bookingrequests;

import com.greenpalmsolutions.security.bookingrequests.api.behavior.FindMyActiveBookingRequests;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
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
class FindsMyActiveBookingRequestsIT {

    @Autowired
    private FindMyActiveBookingRequests findMyActiveBookingRequests;

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
    void findsMyActiveBookingRequests() {
        List<BookingRequestDetails> theActiveBookingRequests =
                findMyActiveBookingRequests.findMyActiveBookingRequests();

        assertThat(theActiveBookingRequests).hasSizeGreaterThan(0);
    }
}
