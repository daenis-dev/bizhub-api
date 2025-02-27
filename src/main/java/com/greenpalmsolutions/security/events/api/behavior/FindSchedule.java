package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.FindScheduleRequest;
import com.greenpalmsolutions.security.events.api.model.ScheduleDetails;

public interface FindSchedule {

    ScheduleDetails findScheduleForRequest(FindScheduleRequest request);
}
