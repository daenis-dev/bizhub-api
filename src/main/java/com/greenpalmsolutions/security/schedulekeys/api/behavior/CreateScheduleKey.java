package com.greenpalmsolutions.security.schedulekeys.api.behavior;

import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;

public interface CreateScheduleKey {

    ScheduleKeyDetails createScheduleKeyForCurrentUser();
}
