package com.greenpalmsolutions.security.bookingrequests.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookingRequestStatusTest {

    private long id;
    private String name;

    private BookingRequestStatus bookingRequestStatus;

    @BeforeEach
    void init() {
        id = 3;
        name = "accepted";

        bookingRequestStatus = new BookingRequestStatus();
        bookingRequestStatus.setId(id);
        bookingRequestStatus.setName(name);
    }

    @Test
    void isEqualToABookingRequestStatusWithTheSameProperties() {
        BookingRequestStatus theOtherBookingRequestStatus = new BookingRequestStatus();
        theOtherBookingRequestStatus.setId(id);
        theOtherBookingRequestStatus.setName(name);

        boolean theBookingRequestStatusEqualsTheOtherBookingRequestStatus =
                bookingRequestStatus.equals(theOtherBookingRequestStatus);

        assertThat(theBookingRequestStatusEqualsTheOtherBookingRequestStatus).isTrue();
    }

    @Test
    void generatesTheHashCode() {
        int theHashCode = bookingRequestStatus.hashCode();

        assertThat(theHashCode).isEqualTo(-2146303728);
    }
}