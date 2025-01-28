package com.greenpalmsolutions.security.accounts.api.controller;

import com.greenpalmsolutions.security.accounts.api.behavior.Login;
import com.greenpalmsolutions.security.accounts.api.behavior.Register;
import com.greenpalmsolutions.security.accounts.api.behavior.ResetPassword;
import com.greenpalmsolutions.security.accounts.api.model.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private Register register;

    @Mock
    private Login login;

    @Mock
    private ResetPassword resetPassword;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AccountController(register, login, resetPassword))
                .build();
    }

    @Test
    void registersAccountForRequest() throws Exception {
        mockMvc.perform(post("/v1/register")
                        .param("email-address", "someone@mail.com")
                        .param("password", "changeit88")
                        .param("confirmed-password", "changeit88"))
                .andExpect(status().isCreated());
    }

    @Test
    void logsInForRequest() throws Exception {
        final String ACCESS_TOKEN = "xyz123";
        final long MILLISECONDS_UNTIL_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24;
        LoginResponse response = new LoginResponse(ACCESS_TOKEN, MILLISECONDS_UNTIL_TOKEN_EXPIRATION, "");

        when(login.loginForRequest(any())).thenReturn(response);

        mockMvc.perform(post("/v1/login")
                        .param("email-address", "someone@mail.com")
                        .param("password", "changeit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(ACCESS_TOKEN)))
                .andExpect(jsonPath("$.millisecondsUntilTokenExpiration", is((int) MILLISECONDS_UNTIL_TOKEN_EXPIRATION)));
    }

    @Test
    void resetsPasswordForRequest() throws Exception {
        mockMvc.perform(post("/v1/reset-password")
                        .param("email-address", "someone@mail.com"))
                .andExpect(status().isOk());
    }
}