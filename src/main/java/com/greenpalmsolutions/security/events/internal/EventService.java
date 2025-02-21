package com.greenpalmsolutions.security.events.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.events.api.behavior.CreateEvent;
import com.greenpalmsolutions.security.events.api.behavior.FindEventDateTimes;
import com.greenpalmsolutions.security.events.api.behavior.FindEvents;
import com.greenpalmsolutions.security.events.api.model.CreateEventRequest;
import com.greenpalmsolutions.security.events.api.model.EventDetails;
import com.greenpalmsolutions.security.events.api.model.EventDateTimeDetails;
import com.greenpalmsolutions.security.events.api.model.FindEventDateTimesRequest;
import com.greenpalmsolutions.security.userfriends.FindCurrentUserFriends;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: IT
@Service
@RequiredArgsConstructor
class EventService implements CreateEvent, FindEvents, FindEventDateTimes {

    private final FindCurrentUserFriends findCurrentUserFriends;
    private final EventRepository eventRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public EventDetails createEventForRequest(CreateEventRequest request) {
        return eventRepository.save(new Event()
                .fromRequestAndUserId(request, findCurrentAccount.getUserIdForCurrentAccount()))
                .getDetails();
    }

    @Override
    public List<EventDateTimeDetails> findScheduleForRequest(FindEventDateTimesRequest request) {
        if (findCurrentUserFriends.doesNotHaveFriendWithUsername(request.getUsername())) {
            throw new InvalidRequestException("Cannot access event date times if the user is not a friend");
        }
        return eventRepository.findScheduleForUser(request.getUsername());
    }

    @Override
    public List<EventDetails> findEventsForCurrentUser() {
        return eventRepository.findByUserId(findCurrentAccount.getUserIdForCurrentAccount())
                .stream().map(Event::getDetails).toList();
    }
}
