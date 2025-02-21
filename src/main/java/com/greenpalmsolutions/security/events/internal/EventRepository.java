package com.greenpalmsolutions.security.events.internal;

import com.greenpalmsolutions.security.events.api.model.EventDateTimeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUserId(String userId);

    // TODO: Query - findScheduleForUser
    List<EventDateTimeDetails> findScheduleForUser(String username);
}
