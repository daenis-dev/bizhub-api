package com.greenpalmsolutions.security.events.api.controller;

import com.greenpalmsolutions.security.events.api.behavior.FindSchedule;
import com.greenpalmsolutions.security.events.api.model.EventDateDetails;
import com.greenpalmsolutions.security.events.api.model.ScheduleDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Mock
    private FindSchedule findSchedule;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ScheduleController(findSchedule))
                .build();
    }

    @Test
    void findsSchedule() throws Exception {
        ScheduleDetails scheduleDetails = new ScheduleDetails("someone@mail.com", List.of(
                new EventDateDetails(ZonedDateTime.now(), ZonedDateTime.now())));

        when(findSchedule.findScheduleForRequest(any())).thenReturn(scheduleDetails);

        mockMvc.perform(get("/v1/schedule")
                        .param("username", "someone@mail.com")
                        .param("schedule-key", "xyzabc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("someone@mail.com")))
                .andExpect(jsonPath("$.eventDateDetails[0].startDateTime", is(notNullValue())));
    }
}
