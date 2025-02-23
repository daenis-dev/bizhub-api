package com.greenpalmsolutions.security.events.api.behavior;

import com.greenpalmsolutions.security.events.api.model.FriendEventDetails;
import com.greenpalmsolutions.security.events.api.model.FindFriendEventsRequest;

import java.util.List;

public interface FindFriendEvents {

    List<FriendEventDetails> findFriendEventsForRequest(FindFriendEventsRequest request);
}
