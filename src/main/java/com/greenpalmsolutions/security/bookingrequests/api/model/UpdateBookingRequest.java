package com.greenpalmsolutions.security.bookingrequests.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class UpdateBookingRequest {

    private long bookingRequestId;
    private String statusName;

    public UpdateBookingRequest withBookingRequestId(String bookingRequestId) {
        if (bookingRequestId == null || bookingRequestId.isEmpty()) {
            throw new InvalidRequestException("Cannot update a booking request without a booking request ID");
        }
        if (!bookingRequestId.matches("\\d*")) {
            throw new InvalidRequestException("Cannot update a booking request for a non numeric booking request ID");
        }
        this.bookingRequestId = Long.parseLong(bookingRequestId);
        return this;
    }

    public UpdateBookingRequest withStatusName(String statusName) {
        if (statusName == null || statusName.isEmpty()) {
            throw new InvalidRequestException("Cannot update a booking request without a status");
        }
        final boolean STATUS_IS_NOT_VALID = !Arrays.asList("accepted", "denied", "canceled").contains(statusName);
        if (STATUS_IS_NOT_VALID) {
            throw new InvalidRequestException("Cannot update a booking request with an invalid status");
        }
        this.statusName = statusName;
        return this;
    }
}
