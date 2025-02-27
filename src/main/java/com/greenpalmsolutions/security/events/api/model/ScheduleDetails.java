package com.greenpalmsolutions.security.events.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleDetails {

    private String username;
    private List<EventDateDetails> eventDateDetails;
}
