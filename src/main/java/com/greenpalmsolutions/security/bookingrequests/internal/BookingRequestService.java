package com.greenpalmsolutions.security.bookingrequests.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.accounts.api.behavior.FindUserIdForUsername;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.FindMyBookingRequests;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.CreateBookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

// TODO: IT
@Service
@RequiredArgsConstructor
class BookingRequestService implements CreateBookingRequest, FindMyBookingRequests {

    private final FindUserIdForUsername findUserIdForUsername;
    private final BookingRequestRepository bookingRequestRepository;
    private final BookingRequestStatusRepository bookingRequestStatusRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public BookingRequestDetails submitForRequest(BookingRequest bookingRequest) {
        com.greenpalmsolutions.security.bookingrequests.internal.BookingRequest model =
                new com.greenpalmsolutions.security.bookingrequests.internal.BookingRequest();
        model.setRequesterEmailAddress(bookingRequest.getRequesterEmailAddress());
        model.setRequesteeUserId(getUserIdForRequesteeEmailAddress(bookingRequest.getRequesteeEmailAddress()));
        model.setEventName(bookingRequest.getEventName());
        model.setStatus(bookingRequestStatusRepository.findByName("pending approval")
                .orElseThrow(() -> new RuntimeException("Cannot find booking request status for name.")));
        model.setStartDateTime(bookingRequest.getStartDateTime());
        model.setEndDateTime(bookingRequest.getEndDateTime());
        model.setCreatedDateTime(ZonedDateTime.now());
        model.setModifiedDateTime(ZonedDateTime.now());

        long id = bookingRequestRepository.save(model).getId();

        return new BookingRequestDetails(
                id,
                bookingRequest.getRequesterEmailAddress(),
                bookingRequest.getRequesteeEmailAddress(),
                bookingRequest.getEventName(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime());
    }

    private String getUserIdForRequesteeEmailAddress(String emailAddress) {
        return findUserIdForUsername.findForUsername(emailAddress);
    }

    @Override
    public List<BookingRequestDetails> findMyBookingRequests() {
        return bookingRequestRepository.findByRequesteeUserId(findCurrentAccount.getUserIdForCurrentAccount());
    }
}
