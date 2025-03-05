package com.greenpalmsolutions.security.bookingrequests.api.controller;

import com.greenpalmsolutions.security.bookingrequests.api.behavior.CreateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.FindMyActiveBookingRequests;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.UpdateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingRequestController {

    private final CreateBookingRequest createBookingRequest;
    private final FindMyActiveBookingRequests findMyActiveBookingRequests;
    private final UpdateBookingRequest updateBookingRequest;

    @PostMapping("/v1/booking-requests")
    public ResponseEntity<BookingRequestDetails> createBookingRequest(
            @RequestParam("requestee-email-address") String requesteeEmailAddress,
            @RequestParam("requester-email-address") String requesterEmailAddress,
            @RequestParam("event-name") String eventName,
            @RequestParam("start-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime startDateTime,
            @RequestParam("end-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime endDateTime) {
        return new ResponseEntity<>(createBookingRequest.createForRequest(
                new com.greenpalmsolutions.security.bookingrequests.api.model.CreateBookingRequest()
                        .withRequesteeEmailAddress(requesteeEmailAddress)
                        .withRequesterEmailAddress(requesterEmailAddress)
                        .withEventName(eventName)
                        .withStartDateTime(startDateTime)
                        .withEndDateTime(endDateTime)),
                HttpStatus.CREATED);
    }

    @GetMapping("/v1/booking-requests")
    public ResponseEntity<List<BookingRequestDetails>> findAllOfMyActiveBookingRequests() {
        return ResponseEntity.ok(findMyActiveBookingRequests.findMyActiveBookingRequests());
    }

    @PutMapping("/v1/booking-requests/{id}")
    public ResponseEntity<BookingRequestDetails> updateBookingRequest(
            @PathVariable("id") String id, @RequestParam("status-name") String statusName) {
        return ResponseEntity.ok(updateBookingRequest.updateBookingForRequest(
                new com.greenpalmsolutions.security.bookingrequests.api.model.UpdateBookingRequest()
                        .withBookingRequestId(id)
                        .withStatusName(statusName)));
    }
}
