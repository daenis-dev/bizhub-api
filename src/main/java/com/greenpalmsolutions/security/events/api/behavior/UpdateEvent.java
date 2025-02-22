package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.EventDetails;
import com.greenpalmsolutions.security.events.api.model.UpdateEventRequest;

public interface UpdateEvent {

    EventDetails updateEventForRequest(UpdateEventRequest request);
}
