package com.greenpalmsolutions.security.events.internal;

import com.greenpalmsolutions.security.events.api.model.FriendEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUserId(String userId);

    @Query("""
    SELECT new com.greenpalmsolutions.security.events.api.model.FriendEventDetails(e.startDateTime, e.endDateTime)
    FROM Event e
    WHERE e.userId = :userId
    """)
    List<FriendEventDetails> findFriendEventsForUser(@Param("userId") String userId);
}
