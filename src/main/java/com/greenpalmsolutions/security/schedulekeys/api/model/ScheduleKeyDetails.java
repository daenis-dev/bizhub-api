package com.greenpalmsolutions.security.schedulekeys.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ScheduleKeyDetails {

    private final long id;
    private final String token;
}
