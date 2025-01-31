package com.greenpalmsolutions.security.commontargetfiles.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommonTargetFileControllerTest {

    @Mock
    private FindCommonTargetFilePaths findCommonTargetFilePaths;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CommonTargetFileController(findCommonTargetFilePaths))
                .build();
    }

    @Test
    void findsCommonTargetFilePathsForWindows() throws Exception {
        List<String> response = Collections.singletonList("C:\\Windows\\test.txt");

        when(findCommonTargetFilePaths.findForWindows()).thenReturn(response);

        mockMvc.perform(get("/v1/common-target-file-paths/windows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("C:\\Windows\\test.txt")));
    }
}