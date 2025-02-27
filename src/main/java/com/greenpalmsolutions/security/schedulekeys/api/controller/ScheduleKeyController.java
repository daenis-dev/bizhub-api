package com.greenpalmsolutions.security.schedulekeys.api.controller;

import com.greenpalmsolutions.security.schedulekeys.api.behavior.CreateScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.DisableScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.FindScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.model.FindScheduleKeyRequest;
import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO: test
@RestController
@AllArgsConstructor
public class ScheduleKeyController {

    private final CreateScheduleKey createScheduleKey;
    private final FindScheduleKey findScheduleKey;
    private final DisableScheduleKey disableScheduleKey;

    @PostMapping("/v1/schedule-keys")
    public ResponseEntity<ScheduleKeyDetails> createScheduleKey() {
        return new ResponseEntity<>(createScheduleKey.createScheduleKeyForCurrentUser(), HttpStatus.CREATED);
    }

    @GetMapping("/v1/schedule-keys")
    public ResponseEntity<ScheduleKeyDetails> findScheduleKey(@RequestParam("username") String username) {
        return ResponseEntity.ok(findScheduleKey.findScheduleKeyForRequest(new FindScheduleKeyRequest().withUsername(username)));
    }

    @DeleteMapping("/v1/schedule-keys")
    public ResponseEntity<ScheduleKeyDetails> disableScheduleKey() {
        disableScheduleKey.disableActiveScheduleKeyForUser();
        return ResponseEntity.ok().build();
    }
}
