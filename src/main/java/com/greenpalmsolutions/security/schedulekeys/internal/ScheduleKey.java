package com.greenpalmsolutions.security.schedulekeys.internal;

import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

// TODO: test
@Entity
@Table(name = "schedule_keys")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class ScheduleKey {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_keys_seq")
    @SequenceGenerator(name = "schedule_keys_seq", sequenceName = "schedule_keys_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "token")
    private String token;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_date_time_in_utc")
    private ZonedDateTime createdDateTime;

    ScheduleKey withUserIdAndToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
        this.isActive = true;
        this.createdDateTime = ZonedDateTime.now();
        return this;
    }

    ScheduleKeyDetails getDetails() {
        return new ScheduleKeyDetails(id, token);
    }
}
