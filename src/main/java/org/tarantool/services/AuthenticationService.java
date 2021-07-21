package org.tarantool.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.tarantool.models.UserModel;
import org.tarantool.models.rest.AuthUserResponse;

@Slf4j
@Service
public class AuthenticationService {

    private final StorageService storageService;

    public AuthenticationService(StorageService storageService) {
        this.storageService = storageService;
    }

    public AuthUserResponse authenticate(String login, String password) {
        UserModel user = storageService.getUserByLogin(login);

        if(user == null) {
            return null;
        }

        String passHash = DigestUtils.md5DigestAsHex(password.getBytes());

        if (user.getPassword().equals(passHash)) {

            AuthUserResponse response = new AuthUserResponse();
            response.setAuthToken(user.getUuid());
            return response;

        } else {
            return null;
        }
    }
}
