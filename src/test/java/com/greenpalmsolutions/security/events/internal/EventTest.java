package com.greenpalmsolutions.security.events.internal;

import com.greenpalmsolutions.security.bookingrequests.internal.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private long id;
    private String userId;
    private String name;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private ZonedDateTime createdDateTime;
    private ZonedDateTime modifiedDateTime;

    private Event event;

    @BeforeEach
    void init() {
        id = 1;
        userId = "11-a";
        name = "Demo Meeting";
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

        event = new Event();
        event.setId(id);
        event.setUserId(userId);
        event.setName(name);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
        event.setCreatedDateTime(createdDateTime);
        event.setModifiedDateTime(modifiedDateTime);
    }

    @Test
    void isEqualToAnEventWithTheSameProperties() {
        Event theOtherEvent = new Event();
        theOtherEvent.setId(id);
        theOtherEvent.setUserId(userId);
        theOtherEvent.setName(name);
        theOtherEvent.setStartDateTime(startDateTime);
        theOtherEvent.setEndDateTime(endDateTime);
        theOtherEvent.setCreatedDateTime(createdDateTime);
        theOtherEvent.setModifiedDateTime(modifiedDateTime);

        boolean theEventEqualsTheOtherEvent = event.equals(theOtherEvent);

        assertThat(theEventEqualsTheOtherEvent).isTrue();
    }

    @Test
    void generatesTheHashCode() {
        int theHashCode = event.hashCode();

        assertThat(theHashCode).isEqualTo(1143415356);
    }
}