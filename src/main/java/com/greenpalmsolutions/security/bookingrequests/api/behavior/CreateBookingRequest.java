package com.greenpalmsolutions.security.bookingrequests.api.behavior;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;

public interface CreateBookingRequest {

    BookingRequestDetails submitForRequest(BookingRequest bookingRequest);
}
