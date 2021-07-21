package org.tarantool.services;

import org.springframework.stereotype.Service;
import org.tarantool.models.UserModel;
import org.tarantool.models.rest.CreateUserRequest;
import org.tarantool.models.rest.UpdateUserRequest;


@Service
public class UserService {
    private final StorageService storageService;

    public UserService(StorageService storageService) {
        this.storageService = storageService;
    }

    public String createUser(CreateUserRequest request) {
        return this.storageService.createUser(request);
    }

    public boolean deleteUser(String login) {
        return this.storageService.deleteUser(login);
    }

    public UserModel getUserByLogin(String login) {
        return this.storageService.getUserByLogin(login);
    }

    public boolean updateUser(String login, UpdateUserRequest request) {
        return this.storageService.updateUser(login, request);
    }
}
