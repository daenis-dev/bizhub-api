package com.greenpalmsolutions.security.schedulekeys.api.controller;

import com.greenpalmsolutions.security.schedulekeys.api.behavior.CreateScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.DisableScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.behavior.FindScheduleKey;
import com.greenpalmsolutions.security.schedulekeys.api.model.ScheduleKeyDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ScheduleKeyControllerTest {

    @Mock
    private CreateScheduleKey createScheduleKey;

    @Mock
    private FindScheduleKey findScheduleKey;

    @Mock
    private DisableScheduleKey disableScheduleKey;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ScheduleKeyController(createScheduleKey, findScheduleKey, disableScheduleKey))
                .build();
    }

    @Test
    void createsTheScheduleKey() throws Exception {
        ScheduleKeyDetails scheduleKeyDetails = new ScheduleKeyDetails(1, "xyz");

        when(createScheduleKey.createScheduleKeyForCurrentUser()).thenReturn(scheduleKeyDetails);

        mockMvc.perform(post("/v1/schedule-keys"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.token", is("xyz")));
    }

    @Test
    void findsTheScheduleKey() throws Exception {
        ScheduleKeyDetails scheduleKeyDetails = new ScheduleKeyDetails(1, "xyz");

        when(findScheduleKey.findScheduleKeyForCurrentUser()).thenReturn(scheduleKeyDetails);

        mockMvc.perform(get("/v1/schedule-keys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.token", is("xyz")));
    }

    @Test
    void disablesTheScheduleKey() throws Exception {
        mockMvc.perform(delete("/v1/schedule-keys"))
                .andExpect(status().isOk());
    }
}