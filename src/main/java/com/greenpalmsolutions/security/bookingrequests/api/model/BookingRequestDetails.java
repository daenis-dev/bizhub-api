package com.greenpalmsolutions.security.bookingrequests.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class BookingRequestDetails {

    private final long id;
    private final String requesterEmailAddress;
    private final String eventName;
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;
}
