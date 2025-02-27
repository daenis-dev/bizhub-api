package com.greenpalmsolutions.security.schedulekeys.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleKeyTest {

    private long id;
    private String userId;
    private String token;
    private boolean isActive;
    private ZonedDateTime createdDateTime;

    private ScheduleKey scheduleKey;

    @BeforeEach
    void init() {
        id = 1;
        userId = "12j";
        token = "xyz";
        isActive = true;
        createdDateTime = ZonedDateTime.now();

        scheduleKey = new ScheduleKey();
        scheduleKey.setId(id);
        scheduleKey.setUserId(userId);
        scheduleKey.setToken(token);
        scheduleKey.setActive(isActive);
        scheduleKey.setCreatedDateTime(ZonedDateTime.of(
                1984, 11, 30, 0, 0, 0, 0,
                ZoneId.of("UTC")));
    }

    @Test
    void isEqualToABackupWithTheSameProperties() {
        ScheduleKey theOtherScheduleKey = new ScheduleKey();
        theOtherScheduleKey.setId(id);
        theOtherScheduleKey.setUserId(userId);
        theOtherScheduleKey.setToken(token);
        theOtherScheduleKey.setActive(isActive);
        theOtherScheduleKey.setCreatedDateTime(ZonedDateTime.of(
                1984, 11, 30, 0, 0, 0, 0,
                ZoneId.of("UTC")));

        boolean theScheduleKeyEqualsTheOtherScheduleKey = scheduleKey.equals(theOtherScheduleKey);

        assertThat(theScheduleKeyEqualsTheOtherScheduleKey).isEqualTo(true);
    }

    @Test
    void generatesHashCode() {
        int theHashCode = scheduleKey.hashCode();

        assertThat(theHashCode).isEqualTo(923408427);
    }
}