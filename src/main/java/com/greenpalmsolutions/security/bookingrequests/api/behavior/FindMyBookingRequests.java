package com.greenpalmsolutions.security.bookingrequests.api.behavior;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;

import java.util.List;

public interface FindMyBookingRequests {

    List<BookingRequestDetails> findMyBookingRequests();
}
