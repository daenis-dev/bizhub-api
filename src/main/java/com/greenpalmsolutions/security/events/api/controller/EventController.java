package com.greenpalmsolutions.security.events.api.controller;

import com.greenpalmsolutions.security.events.api.behavior.CreateEvent;
import com.greenpalmsolutions.security.events.api.behavior.FindEventDateTimes;
import com.greenpalmsolutions.security.events.api.behavior.FindEvents;
import com.greenpalmsolutions.security.events.api.model.CreateEventRequest;
import com.greenpalmsolutions.security.events.api.model.EventDateTimeDetails;
import com.greenpalmsolutions.security.events.api.model.EventDetails;
import com.greenpalmsolutions.security.events.api.model.FindEventDateTimesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final CreateEvent createEvent;
    private final FindEvents findEvents;
    private final FindEventDateTimes findEventDateTimes;

    @PostMapping("/v1/events")
    public ResponseEntity<EventDetails> createEvent(
            @RequestParam("name") String name,
            @RequestParam("start-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime startDateTime,
            @RequestParam("end-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime endDateTime) {
        return new ResponseEntity<>(
                createEvent.createEventForRequest(new CreateEventRequest()
                        .withName(name)
                        .withStartDateTime(startDateTime)
                        .withEndDateTime(endDateTime)),
                HttpStatus.CREATED);
    }

    @GetMapping("/v1/events")
    public ResponseEntity<List<EventDetails>> findEvents() {
        return ResponseEntity.ok(findEvents.findEventsForCurrentUser());
    }

    @GetMapping("/v1/event-date-times")
    public ResponseEntity<List<EventDateTimeDetails>> findEventDateTimes(@RequestParam("username") String username) {
        return ResponseEntity.ok(findEventDateTimes.findScheduleForRequest(
                new FindEventDateTimesRequest().withUsername(username)));
    }
}
