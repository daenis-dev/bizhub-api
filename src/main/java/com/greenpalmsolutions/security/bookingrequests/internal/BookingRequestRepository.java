package com.greenpalmsolutions.security.bookingrequests.internal;

import com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {

    @Query("""
        SELECT new com.greenpalmsolutions.security.bookingrequests.api.model.BookingRequestDetails(
            br.id, br.requesterEmailAddress, br.requesteeEmailAddress, br.eventName, br.startDateTime, br.endDateTime)
        FROM BookingRequest br
        WHERE br.requesteeUserId = :requesteeUserId
        AND br.status.name = 'pending approval'
    """)
    List<BookingRequestDetails> findByRequesteeUserId(@Param("requesteeUserId") String requesteeUserId);
}
