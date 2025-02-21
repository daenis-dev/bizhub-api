package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.EventDateTimeDetails;
import com.greenpalmsolutions.security.events.api.model.FindEventDateTimesRequest;

import java.util.List;

public interface FindEventDateTimes {

    List<EventDateTimeDetails> findScheduleForRequest(FindEventDateTimesRequest request);
}
