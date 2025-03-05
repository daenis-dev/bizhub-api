package com.greenpalmsolutions.security.bookingrequests.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

import java.time.ZonedDateTime;

// TODO: test
@Getter
public class BookingRequest {

    private String requesterEmailAddress;
    private String requesteeEmailAddress;
    private String eventName;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    public BookingRequest withRequesterEmailAddress(String requesterEmailAddress) {
        if (requesterEmailAddress == null || requesterEmailAddress.isEmpty()) {
            throw new InvalidRequestException("Requester email address is required to create a booking request");
        }
        if (new EmailAddressValidator().isNotValidFormat(requesterEmailAddress)) {
            throw new InvalidRequestException("Requester email address must be in a valid format to create a booking request");
        }
        this.requesterEmailAddress = requesterEmailAddress;
        return this;
    }

    public BookingRequest withRequesteeEmailAddress(String requesteeEmailAddress) {
        if (requesteeEmailAddress == null || requesteeEmailAddress.isEmpty()) {
            throw new InvalidRequestException("Requestee email address is required to create a booking request");
        }
        if (new EmailAddressValidator().isNotValidFormat(requesteeEmailAddress)) {
            throw new InvalidRequestException("Requestee email address must be in a valid format to create a booking request");
        }
        this.requesteeEmailAddress = requesteeEmailAddress;
        return this;
    }

    public BookingRequest withEventName(String eventName) {
        if (eventName == null || eventName.isEmpty()) {
            throw new InvalidRequestException("Event name is required to create a booking request");
        }
        this.eventName = eventName;
        return this;
    }

    public BookingRequest withStartDateTime(ZonedDateTime startDateTime) {
        if (startDateTime == null) {
            throw new InvalidRequestException("Start date time is required to create a booking request");
        }
        this.startDateTime = startDateTime;
        return this;
    }

    public BookingRequest withEndDateTime(ZonedDateTime endDateTime) {
        if (endDateTime == null) {
            throw new InvalidRequestException("End date time is required to create a booking request");
        }
        this.endDateTime = endDateTime;
        return this;
    }
}
