package com.greenpalmsolutions.security.bookingrequests.internal;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {

    Optional<BookingRequest> findByIdAndRequesteeUserId(Long id, String requesteeUserId);

    @Query("""
        SELECT new com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails(
            br.id, br.requesterEmailAddress, br.eventName, br.status.name, br.startDateTime, br.endDateTime)
        FROM BookingRequest br
        WHERE br.requesteeUserId = :requesteeUserId
        AND (br.status.name = 'pending approval' OR br.status.name = 'accepted')
    """)
    List<BookingRequestDetails> findByRequesteeUserId(@Param("requesteeUserId") String requesteeUserId);
}
