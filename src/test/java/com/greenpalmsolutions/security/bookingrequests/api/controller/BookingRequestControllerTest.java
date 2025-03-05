package com.greenpalmsolutions.security.bookingrequests.api.controller;

import com.greenpalmsolutions.security.bookingrequests.api.behavior.CreateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.FindMyActiveBookingRequests;
import com.greenpalmsolutions.security.bookingrequests.api.behavior.UpdateBookingRequest;
import com.greenpalmsolutions.security.bookingrequests.api.controller.BookingRequestController;
import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingRequestControllerTest {

    @Mock
    private CreateBookingRequest createBookingRequest;

    @Mock
    private FindMyActiveBookingRequests findMyActiveBookingRequests;

    @Mock
    private UpdateBookingRequest updateBookingRequest;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BookingRequestController(createBookingRequest, findMyActiveBookingRequests, updateBookingRequest))
                .build();
    }

    @Test
    void createsTheBookingRequest() throws Exception {
        BookingRequestDetails theDetails = new BookingRequestDetails(
                1, "someone@mail.com", "meeting",
                "pending approval", ZonedDateTime.now(), ZonedDateTime.now());

        when(createBookingRequest.createForRequest(any())).thenReturn(theDetails);

        mockMvc.perform(post("/v1/booking-requests")
                        .param("requestee-email-address", "someoneelse@mail.com")
                        .param("requester-email-address", "someone@mail.com")
                        .param("event-name", "Tour")
                        .param("start-date-time",
                                ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE.ISO_DATE_TIME))
                        .param("end-date-time",
                                ZonedDateTime.now().plusHours(1).format(DateTimeFormatter.ISO_DATE.ISO_DATE_TIME)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void findsAllOfMyActiveBookingRequest() throws Exception {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        BookingRequestDetails theDetails = new BookingRequestDetails(
                1, "someone@mail.com", "meeting",
                "pending approval", nowUtc, nowUtc.plusHours(2));

        when(findMyActiveBookingRequests.findMyActiveBookingRequests()).thenReturn(Collections.singletonList(theDetails));

        mockMvc.perform(get("/v1/booking-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].startDateTime", is(theDetails.getStartDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))))
                .andExpect(jsonPath("$[0].endDateTime", is(theDetails.getEndDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))));
    }

    @Test
    void updatesTheBookingRequest() throws Exception {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        BookingRequestDetails theDetails = new BookingRequestDetails(
                1, "someone@mail.com", "meeting",
                "pending approval", nowUtc, nowUtc.plusHours(2));

        when(updateBookingRequest.updateBookingForRequest(any())).thenReturn(theDetails);

        mockMvc.perform(put("/v1/booking-requests/1")
                        .param("status-name", "denied"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }
}