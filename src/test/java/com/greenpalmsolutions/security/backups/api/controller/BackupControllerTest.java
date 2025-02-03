package com.greenpalmsolutions.security.backups.api.controller;

import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackups;
import com.greenpalmsolutions.security.backups.api.behavior.FindBackupFileNames;
import com.greenpalmsolutions.security.backups.api.behavior.UploadBackups;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.UploadBackupsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BackupControllerTest {

    @Mock
    private UploadBackups uploadBackups;

    @Mock
    private DownloadBackups downloadBackups;

    @Mock
    private FindBackupFileNames findBackupFileNames;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BackupController(uploadBackups, downloadBackups, findBackupFileNames))
                .build();
    }

    @Test
    void createsTheBackups() throws Exception {
        UploadBackupsResponse theResponse = new UploadBackupsResponse(444);

        when(uploadBackups.uploadBackupsForRequest(any())).thenReturn(theResponse);

        MockMultipartFile file1 = new MockMultipartFile(
                "files", "file1.txt", "text/plain", "Hello, World!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile(
                "files", "file2.txt", "text/plain", "Another file.".getBytes());

        mockMvc.perform(multipart("/v1/backups")
                        .file(file1)
                        .file(file2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.formattedSize", is("444 bytes")));
    }

    @Test
    void downloadsTheBackups() throws Exception {
        final byte[] BACKUPS = new byte[]{1, 22, 33};

        when(downloadBackups.downloadBackupsForRequest(any())).thenReturn(BACKUPS);

        mockMvc.perform(get("/v1/backups")
                        .param("file-names", "file1.txt,file2.txt"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(BACKUPS));
    }

    @Test
    void findsTheBackupFileNames() throws Exception {
        List<String> theBackupFileNames = Arrays.asList("file1.txt", "file2.txt");

        when(findBackupFileNames.findFileNames()).thenReturn(theBackupFileNames);

        mockMvc.perform(get("/v1/backups/file-names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("file1.txt")));
    }
}