package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.CreateEventRequest;
import com.greenpalmsolutions.security.events.api.model.EventDetails;

public interface CreateEvent {

    EventDetails createEventForRequest(CreateEventRequest request);
}
