package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.DeleteEventRequest;

public interface DeleteEvent {

    void deleteEventForRequest(DeleteEventRequest request);
}
