package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.EventDetails;

import java.util.List;

public interface FindEvents {

    List<EventDetails> findEventsForCurrentUser();
}
