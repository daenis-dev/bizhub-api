package com.greenpalmsolutions.security.bookingrequests.api.behavior;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;

public interface CreateBookingRequest {

    BookingRequestDetails createForRequest(
            com.greenpalmsolutions.security.bookingrequests.api.model.CreateBookingRequest request);
}
