package it.bookingrequests;

import com.greenpalmsolutions.security.bookingrequests.api.behavior.UpdateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
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
class UpdateBookingRequestIT {

    @Autowired
    private UpdateBookingRequest updateBookingRequest;

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
    void updatesTheBookingRequest() {
        com.greenpalmsolutions.security.bookingrequests.api.model.UpdateBookingRequest request =
                new com.greenpalmsolutions.security.bookingrequests.api.model.UpdateBookingRequest()
                        .withBookingRequestId("3")
                        .withStatusName("accepted");

        BookingRequestDetails theDetails = updateBookingRequest.updateBookingForRequest(request);

        assertThat(theDetails.getStatus()).isEqualTo("accepted");
    }

    @AfterEach
    void cleanUp() {
        entityManager.createNativeQuery(
                        "UPDATE booking_requests " +
                                "SET status_id = ("
                                    + "SELECT booking_request_statuses.id FROM booking_request_statuses "
                                    + "WHERE name = :statusName) "
                                + "WHERE id = :id")
                .setParameter("statusName", "pending approval")
                .setParameter("id", 3L)
                .executeUpdate();
    }
}
