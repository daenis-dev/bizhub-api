package com.greenpalmsolutions.security.bookingrequests.api.behavior;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;

public interface CreateBookingRequest {

    BookingRequestDetails submitForRequest(
            com.greenpalmsolutions.security.bookingrequests.api.model.CreateBookingRequest request);
}
