package com.greenpalmsolutions.security.events.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUserId(String userId);

    @Query("""
        SELECT COUNT(e) > 0 FROM Event e WHERE e.userId = :userId AND ((e.startDateTime > :startDateTime AND e.endDateTime < :endDateTime))
       """)
    boolean eventExistsBetweenStartDateTimeAndEndDateTimeForUserId(
            @Param("startDateTime") ZonedDateTime startDateTime,
            @Param("endDateTime") ZonedDateTime endDateTime,
            @Param("userId") String userId
    );

    @Query("""
        SELECT COUNT(e) > 0 FROM Event e WHERE e.userId = :userId AND ((e.startDateTime > :startDateTime AND e.endDateTime < :endDateTime) AND e.id <> :eventId)
    """)
    boolean eventExistsBetweenStartDateTimeAndEndDateTimeForUserIdAndEventId(
            @Param("startDateTime") ZonedDateTime startDateTime,
            @Param("endDateTime") ZonedDateTime endDateTime,
            @Param("userId") String userId,
            @Param("eventId") long eventId
    );


}
