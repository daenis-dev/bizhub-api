package com.greenpalmsolutions.security.artifacts.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.ArtifactDetails;
import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsResponse;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArtifactControllerTest {

    @Mock
    private CreateArtifacts createArtifacts;

    @Mock
    private ValidateArtifacts validateArtifacts;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ArtifactController(createArtifacts, validateArtifacts))
                .build();
    }

    @Test
    void createsTheArtifact() throws Exception {
        ArtifactRequest request = new ArtifactRequest();
        request.setFilePath("C:\\Windows\\System32\\ntoskrnl.exe");
        request.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1");

        mockMvc.perform(post("/v1/artifacts")
                        .contentType(ContentType.APPLICATION_JSON.toString())
                        .content(new ObjectMapper().writeValueAsString(Collections.singletonList(request))))
                .andExpect(status().isCreated());
    }

    @Test
    void validatesTheArtifacts() throws Exception {
        String modifiedFilePath = "/something/modified.txt";
        ValidateArtifactsResponse response = new ValidateArtifactsResponse();
        response.addCorruptArtifactFilePath(modifiedFilePath);

        ArtifactRequest request = new ArtifactRequest();
        request.setFilePath("C:\\Windows\\System32\\ntoskrnl.exe");
        request.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1");

        when(validateArtifacts.validateArtifactsForRequests(any())).thenReturn(response);

        mockMvc.perform(get("/v1/artifacts/validate")
                        .contentType(ContentType.APPLICATION_JSON.toString())
                        .content(new ObjectMapper().writeValueAsString(Collections.singletonList(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.corruptArtifactFilePaths[0]", is(modifiedFilePath)));
    }
}