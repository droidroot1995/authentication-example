package org.tarantool.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tarantool.models.rest.AuthUserRequest;
import org.tarantool.models.rest.AuthUserResponse;
import org.tarantool.services.AuthenticationService;

@Slf4j
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login", produces={"application/json"})
    public ResponseEntity<AuthUserResponse> authenticate(@RequestBody AuthUserRequest request) {

        String login = request.getLogin();
        String password = request.getPassword();

        AuthUserResponse response = this.authenticationService.authenticate(login, password);

        if(response != null) {

            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(response);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
