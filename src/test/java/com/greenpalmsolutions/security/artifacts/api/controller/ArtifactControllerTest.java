package com.greenpalmsolutions.security.artifacts.api.controller;

import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactResponse;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactResponse;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsResponse;
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
    private CreateArtifact createArtifact;

    @Mock
    private ValidateArtifact validateArtifact;

    @Mock
    private ValidateArtifacts validateArtifacts;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ArtifactController(createArtifact, validateArtifact, validateArtifacts))
                .build();
    }

    @Test
    void createsTheArtifact() throws Exception {
        final long ID = 1L;
        final String ARTIFACT_NAME = "Windows Kernel";
        final String FILE_PATH = "C:\\Windows\\System32\\ntoskrnl.exe";
        CreateArtifactResponse response = new CreateArtifactResponse(ID, ARTIFACT_NAME, FILE_PATH);

        when(createArtifact.createArtifactForRequest(any())).thenReturn(response);

        mockMvc.perform(post("/v1/artifacts")
                        .param("name", ARTIFACT_NAME)
                        .param("file-path", FILE_PATH)
                        .param("hash", "4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) ID)))
                .andExpect(jsonPath("$.name", is(ARTIFACT_NAME)))
                .andExpect(jsonPath("$.filePath", is(FILE_PATH)));
    }

    @Test
    void validatesTheArtifact() throws Exception {
        final String MESSAGE = "Artifact is no longer valid; hash has been updated";
        ValidateArtifactResponse response = new ValidateArtifactResponse(false);

        when(validateArtifact.validateArtifactForRequest(any())).thenReturn(response);

        mockMvc.perform(get("/v1/artifacts/1")
                        .param("hash", "4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", is(false)))
                .andExpect(jsonPath("$.message", is(MESSAGE)));
    }

    @Test
    void validatesTheArtifacts() throws Exception {
        String modifiedFilePath = "/something/modified.txt";
        ValidateArtifactsResponse response = new ValidateArtifactsResponse();
        response.addCorruptArtifactFilePath(modifiedFilePath);

        when(validateArtifacts.validateArtifactsForRequest(any())).thenReturn(response);

        mockMvc.perform(get("/v1/artifacts")
                        .param("artifact-hashes", modifiedFilePath + ":" + "7f89ds7f89ds798f"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.corruptArtifactFilePaths[0]", is(modifiedFilePath)));
    }
}