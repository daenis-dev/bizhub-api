package com.greenpalmsolutions.security.bookingrequests.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BookingRequestStatusRepository extends JpaRepository<BookingRequestStatus, Long> {

    Optional<BookingRequestStatus> findByName(String name);
}
