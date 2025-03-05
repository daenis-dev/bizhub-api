package com.greenpalmsolutions.security.bookingrequests.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.accounts.api.behavior.FindUserIdForUsername;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.CreateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.FindMyActiveBookingRequests;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.UpdateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class BookingRequestService implements CreateBookingRequest, FindMyActiveBookingRequests, UpdateBookingRequest {

    private final FindUserIdForUsername findUserIdForUsername;
    private final BookingRequestRepository bookingRequestRepository;
    private final BookingRequestStatusRepository bookingRequestStatusRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public BookingRequestDetails createForRequest(
            com.greenpalmsolutions.security.bookingrequests.api.model.CreateBookingRequest request) {
        BookingRequest bookingRequest =
                new BookingRequest();
        bookingRequest.setRequesterEmailAddress(request.getRequesterEmailAddress());
        bookingRequest.setRequesteeUserId(getUserIdForRequesteeEmailAddress(request.getRequesteeEmailAddress()));
        bookingRequest.setEventName(request.getEventName());
        bookingRequest.setStatus(bookingRequestStatusRepository.findByName("pending approval")
                .orElseThrow(() -> new RuntimeException("Cannot find booking request status for name.")));
        bookingRequest.setStartDateTime(request.getStartDateTime());
        bookingRequest.setEndDateTime(request.getEndDateTime());
        bookingRequest.setCreatedDateTime(ZonedDateTime.now());
        bookingRequest.setModifiedDateTime(ZonedDateTime.now());

        long id = bookingRequestRepository.save(bookingRequest).getId();

        return new BookingRequestDetails(
                id,
                request.getRequesterEmailAddress(),
                request.getEventName(),
                bookingRequest.getStatusName(),
                request.getStartDateTime(),
                request.getEndDateTime());
    }

    private String getUserIdForRequesteeEmailAddress(String emailAddress) {
        return findUserIdForUsername.findForUsername(emailAddress);
    }

    @Override
    public List<BookingRequestDetails> findMyActiveBookingRequests() {
        return bookingRequestRepository.findByRequesteeUserId(findCurrentAccount.getUserIdForCurrentAccount());
    }

    @Override
    public BookingRequestDetails updateBookingForRequest(
            com.greenpalmsolutions.security.bookingrequests.api.model.UpdateBookingRequest request) {
        BookingRequest bookingRequest = bookingRequestRepository.findByIdAndRequesteeUserId(
                request.getBookingRequestId(), findCurrentAccount.getUserIdForCurrentAccount())
                .orElseThrow(() -> new InvalidRequestException("Booking request does not exist for ID or requestee ID"));

        bookingRequest.setStatus(bookingRequestStatusRepository.findByName(request.getStatusName())
                .orElseThrow(() -> new InvalidRequestException("Booking request status does not exist for name")));

        bookingRequestRepository.save(bookingRequest);

        return new BookingRequestDetails(
                bookingRequest.getId(),
                bookingRequest.getRequesterEmailAddress(),
                bookingRequest.getEventName(),
                bookingRequest.getStatusName(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime());
    }
}
