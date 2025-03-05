package com.greenpalmsolutions.security.bookingrequests.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookingRequestTest {

    private long bookingRequestStatusId;
    private String bookingRequestStatusName;
    private BookingRequestStatus bookingRequestStatus;

    private long id;
    private String requesteeUserId;
    private String requesterEmailAddress;
    private String eventName;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private ZonedDateTime createdDateTime;
    private ZonedDateTime modifiedDateTime;

    private BookingRequest bookingRequest;

    @BeforeEach
    void init() {
        bookingRequestStatusId = 3;
        bookingRequestStatusName = "accepted";

        bookingRequestStatus = new BookingRequestStatus();
        bookingRequestStatus.setId(bookingRequestStatusId);
        bookingRequestStatus.setName(bookingRequestStatusName);

        id = 1;
        requesteeUserId = "aa-1";
        requesterEmailAddress = "someone@mail.com";
        eventName = "Tour";
        bookingRequestStatus.setRequestIsPendingApproval(true);
        startDateTime = ZonedDateTime.of(
                1984, 11, 30, 0, 0, 0, 0,
                ZoneId.of("UTC"));
        endDateTime = ZonedDateTime.of(
                1984, 11, 30, 1, 0, 0, 0,
                ZoneId.of("UTC"));
        createdDateTime = ZonedDateTime.of(
                1984, 11, 28, 0, 0, 0, 0,
                ZoneId.of("UTC"));
        modifiedDateTime = ZonedDateTime.of(
                1984, 11, 28, 0, 0, 0, 0,
                ZoneId.of("UTC"));

        bookingRequest = new BookingRequest();
        bookingRequest.setId(id);
        bookingRequest.setRequesteeUserId(requesteeUserId);
        bookingRequest.setRequesterEmailAddress(requesterEmailAddress);
        bookingRequest.setEventName(eventName);
        bookingRequest.setStatus(bookingRequestStatus);
        bookingRequest.setStartDateTime(startDateTime);
        bookingRequest.setEndDateTime(endDateTime);
        bookingRequest.setCreatedDateTime(createdDateTime);
        bookingRequest.setModifiedDateTime(modifiedDateTime);
    }

    @Test
    void isEqualToABookingRequestWithTheSameProperties() {
        BookingRequest theOtherBookingRequest = new BookingRequest();
        theOtherBookingRequest.setId(id);
        theOtherBookingRequest.setRequesteeUserId(requesteeUserId);
        theOtherBookingRequest.setRequesterEmailAddress(requesterEmailAddress);
        theOtherBookingRequest.setEventName(eventName);
        theOtherBookingRequest.setStatus(bookingRequestStatus);
        theOtherBookingRequest.setStartDateTime(startDateTime);
        theOtherBookingRequest.setEndDateTime(endDateTime);
        theOtherBookingRequest.setCreatedDateTime(createdDateTime);
        theOtherBookingRequest.setModifiedDateTime(modifiedDateTime);

        boolean theBookingRequestEqualsTheOtherBookingRequest = bookingRequest.equals(theOtherBookingRequest);

        assertThat(theBookingRequestEqualsTheOtherBookingRequest).isTrue();
    }

    @Test
    void generatesTheHashCode() {
        int theHashCode = bookingRequest.hashCode();

        assertThat(theHashCode).isEqualTo(-779166124);
    }
}