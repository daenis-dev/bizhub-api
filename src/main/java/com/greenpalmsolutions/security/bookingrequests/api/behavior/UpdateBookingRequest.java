package com.greenpalmsolutions.security.bookingrequests.api.behavior;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;

public interface UpdateBookingRequest {

    BookingRequestDetails updateBookingForRequest(
            com.greenpalmsolutions.security.bookingrequests.api.model.UpdateBookingRequest request);
}
