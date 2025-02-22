package com.greenpalmsolutions.security.events.api.controller;

import com.greenpalmsolutions.security.events.api.behavior.*;
import com.greenpalmsolutions.security.events.api.model.EventDateTimeDetails;
import com.greenpalmsolutions.security.events.api.model.EventDetails;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private CreateEvent createEvent;

    @Mock
    private FindEvents findEvents;

    @Mock
    private FindEventDateTimes findEventDateTimes;

    @Mock
    private UpdateEvent updateEvent;

    @Mock
    private DeleteEvent deleteEvent;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EventController(createEvent, findEvents, findEventDateTimes, updateEvent, deleteEvent))
                .build();
    }

    @Test
    void createsEvent() throws Exception {
        EventDetails eventDetails = new EventDetails(
                1, "Meeting", ZonedDateTime.now(), ZonedDateTime.now().plusHours(2));

        when(createEvent.createEventForRequest(any())).thenReturn(eventDetails);

        mockMvc.perform(post("/v1/events")
                        .param("name", "Meeting One")
                        .param("start-date-time",
                                ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE.ISO_DATE_TIME))
                        .param("end-date-time",
                                    ZonedDateTime.now().plusHours(1).format(DateTimeFormatter.ISO_DATE.ISO_DATE_TIME)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void findsEvents() throws Exception {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        EventDetails eventDetails = new EventDetails(
                1, "Meeting", nowUtc, nowUtc.plusHours(2));

        when(findEvents.findEventsForCurrentUser()).thenReturn(Collections.singletonList(eventDetails));

        mockMvc.perform(get("/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].startDateTime", is(eventDetails.getStartDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))))
                .andExpect(jsonPath("$[0].endDateTime", is(eventDetails.getEndDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))));
    }

    @Test
    void findsEventDateTimes() throws Exception {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        EventDateTimeDetails eventDateTimeDetails = new EventDateTimeDetails(
                nowUtc,
                nowUtc.plusHours(2));

        when(findEventDateTimes.findScheduleForRequest(any())).thenReturn(
                Collections.singletonList(eventDateTimeDetails));

        mockMvc.perform(get("/v1/event-date-times")
                        .param("username", "someone@mail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].startDateTime", is(eventDateTimeDetails.getStartDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))))
                .andExpect(jsonPath("$[0].endDateTime", is(eventDateTimeDetails.getEndDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))));
    }
}