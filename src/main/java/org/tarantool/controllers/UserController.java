package org.tarantool.controllers;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tarantool.models.UserModel;
import org.tarantool.models.rest.CreateUserRequest;
import org.tarantool.models.rest.CreateUserResponse;
import org.tarantool.models.rest.GetUserInfoResponse;
import org.tarantool.models.rest.UpdateUserRequest;
import org.tarantool.services.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces={"application/json"})
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody CreateUserRequest request) {
        String login = this.userService.createUser(request);

        if(login != null) {

            CreateUserResponse response = new CreateUserResponse();
            response.setLogin(login);

            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(response);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{login}", produces={"application/json"})
    public ResponseEntity<GetUserInfoResponse> getUserInfo(
            @PathVariable("login") String login) {
        UserModel model = this.userService.getUserByLogin(login);
        if(model != null) {
            GetUserInfoResponse response = new GetUserInfoResponse();
            response.setUuid(model.getUuid());
            response.setLogin(model.getLogin());
            response.setPassword(model.getPassword());

            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .body(response);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{login}", produces={"application/json"})
    public ResponseEntity<Void> updateUser(
            @PathVariable("login") String login,
            @RequestBody UpdateUserRequest request) {
        boolean updated = this.userService.updateUser(login, request);

        if(updated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{login}", produces={"application/json"})
    public ResponseEntity<Void> deleteUser(
            @PathVariable("login") String login) {
        boolean deleted = this.userService.deleteUser(login);

        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .cacheControl(CacheControl.noCache())
                    .build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
