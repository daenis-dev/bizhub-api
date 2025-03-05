package com.greenpalmsolutions.security.bookingrequests.internal;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BookingRequestStatusRepository {

    Optional<BookingRequestStatus> findByName(String name);
}
