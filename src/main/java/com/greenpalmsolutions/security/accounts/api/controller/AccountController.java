package com.greenpalmsolutions.security.accounts.api.controller;

import com.greenpalmsolutions.security.accounts.api.behavior.Login;
import com.greenpalmsolutions.security.accounts.api.behavior.Register;
import com.greenpalmsolutions.security.accounts.api.behavior.ResetPassword;
import com.greenpalmsolutions.security.accounts.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final Register register;
    private final Login login;
    private final ResetPassword resetPassword;

    @PostMapping("/v1/register")
    public ResponseEntity<?> register(
            @RequestParam("email-address") String emailAddress,
            @RequestParam("password") String password,
            @RequestParam("confirmed-password") String confirmedPassword) {
        register.registerAccountForRequest(new RegistrationRequest()
                        .withEmailAddress(emailAddress)
                        .withPassword(password)
                        .confirmPassword(confirmedPassword));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/v1/login")
    public ResponseEntity<LoginResponse> login(
            @RequestParam("email-address") String emailAddress,
            @RequestParam("password") String password) {
        return ResponseEntity.ok(
                login.loginForRequest(new LoginRequest()
                        .withEmailAddress(emailAddress)
                        .withPassword(password)));
    }

    @PostMapping("/v1/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("email-address") String emailAddress) {
        resetPassword.sendEmailToResetPasswordForRequest(new ResetPasswordRequest()
                .withEmailAddress(emailAddress));
        return ResponseEntity.ok().build();
    }
}
