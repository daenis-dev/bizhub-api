package it.bookingrequests;

import com.greenpalmsolutions.security.bookingrequests.api.behavior.CreateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CreatesBookingRequestIT {

    @Autowired
    private CreateBookingRequest createBookingRequest;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Modifying
    @Test
    void createsTheBookingRequest() {
        ZonedDateTime startDateTime = ZonedDateTime.now().plusHours(2);
        ZonedDateTime endDateTime = ZonedDateTime.now().plusHours(3);
        com.greenpalmsolutions.security.bookingrequests.api.model.CreateBookingRequest request =
                new com.greenpalmsolutions.security.bookingrequests.api.model.CreateBookingRequest()
                        .withRequesteeEmailAddress("someone@mail.com")
                        .withRequesterEmailAddress("someoneelse@mail.com")
                        .withEventName("test")
                        .withStartDateTime(startDateTime)
                        .withEndDateTime(endDateTime);

        BookingRequestDetails theDetails = createBookingRequest.createForRequest(request);

        assertThat(theDetails.getId()).isGreaterThan(0);
        assertThat(theDetails.getEventName()).isEqualTo("test");
        assertThat(theDetails.getStatus()).isEqualTo("pending approval");
        assertThat(theDetails.getRequesterEmailAddress()).isEqualTo("someoneelse@mail.com");
        assertThat(theDetails.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(theDetails.getEndDateTime()).isEqualTo(endDateTime);
    }

    @AfterEach
    void cleanUp() {
        entityManager.createQuery("DELETE FROM BookingRequest br WHERE br.requesteeUserId = :userId AND br.eventName = :eventName")
                .setParameter("userId", "456-def")
                .setParameter("eventName", "test")
                .executeUpdate();
    }
}
