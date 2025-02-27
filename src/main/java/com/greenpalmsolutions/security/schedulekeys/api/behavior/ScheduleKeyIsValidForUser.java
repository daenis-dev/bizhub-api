package com.greenpalmsolutions.security.schedulekeys.api.behavior;

public interface ScheduleKeyIsValidForUser {

    boolean scheduleKeyIsValidForUser(String scheduleKey, String userId);
}
