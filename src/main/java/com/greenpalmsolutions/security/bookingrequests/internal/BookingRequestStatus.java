package com.greenpalmsolutions.security.bookingrequests.internal;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// TODO: test
@Entity
@Table(name = "booking_request_statuses")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class BookingRequestStatus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_request_statuses_seq")
    @SequenceGenerator(name = "booking_request_statuses_seq", sequenceName = "booking_request_statuses_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "request_is_pending_approval")
    private boolean requestIsPendingApproval;
}
