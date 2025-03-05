package com.greenpalmsolutions.security.bookingrequests.internal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

// TODO: test
@Entity
@Table(name = "booking_requests")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class BookingRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_requests_seq")
    @SequenceGenerator(name = "booking_requests_seq", sequenceName = "booking_requests_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "requestee_user_id")
    private String requesteeUserId;

    @Column(name = "requester_email_address")
    private String requesterEmailAddress;

    @Column(name = "event_name")
    private String eventName;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private BookingRequestStatus status;

    @Column(name = "start_date_time_in_utc")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time_in_utc")
    private ZonedDateTime endDateTime;

    @Column(name = "created_date_time_in_utc")
    private ZonedDateTime createdDateTime;

    @Column(name = "modified_date_time_in_utc")
    private ZonedDateTime modifiedDateTime;
}
