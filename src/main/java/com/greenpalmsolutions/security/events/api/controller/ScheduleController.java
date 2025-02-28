package com.greenpalmsolutions.security.events.api.controller;

import com.greenpalmsolutions.security.events.api.behavior.FindSchedule;
import com.greenpalmsolutions.security.events.api.model.FindScheduleRequest;
import com.greenpalmsolutions.security.events.api.model.ScheduleDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final FindSchedule findSchedule;

    @GetMapping("/v1/schedules")
    public ResponseEntity<ScheduleDetails> findScheduleForUser(
            @RequestParam("username") String username, @RequestParam("schedule-key") String scheduleKey) {
        return ResponseEntity.ok(findSchedule.findScheduleForRequest(
                new FindScheduleRequest().withUsername(username).withScheduleKey(scheduleKey)));
    }
}
