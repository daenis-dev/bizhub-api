package com.greenpalmsolutions.security.events.internal;

import com.greenpalmsolutions.security.events.api.model.CreateEventRequest;
import com.greenpalmsolutions.security.events.api.model.EventDetails;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "events")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
    @SequenceGenerator(name = "events_seq", sequenceName = "events_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "start_date_time_in_utc")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time_in_utc")
    private ZonedDateTime endDateTime;

    @Column(name = "created_date_time_in_utc")
    private ZonedDateTime createdDateTime;

    @Column(name = "modified_date_time_in_utc")
    private ZonedDateTime modifiedDateTime;

    Event fromRequestAndUserId(CreateEventRequest request, String userId) {
        this.name = request.getName();
        this.userId = userId;
        this.startDateTime = request.getStartDateTime();
        this.endDateTime = request.getEndDateTime();
        this.createdDateTime = ZonedDateTime.now();
        this.modifiedDateTime = ZonedDateTime.now();
        return this;
    }

    EventDetails getDetails() {
        return new EventDetails(id, name, startDateTime, endDateTime);
    }
}
