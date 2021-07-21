package org.tarantool.services;

import org.tarantool.models.UserModel;
import org.tarantool.models.rest.CreateUserRequest;
import org.tarantool.models.rest.UpdateUserRequest;

public interface StorageService {

    UserModel getUserByLogin(String login);

    String createUser(CreateUserRequest request);

    boolean updateUser(String login, UpdateUserRequest request);

    boolean deleteUser(String login);
}
