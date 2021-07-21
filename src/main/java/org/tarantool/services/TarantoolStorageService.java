package org.tarantool.services;

import io.tarantool.driver.api.TarantoolClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.tarantool.models.UserModel;
import org.tarantool.models.rest.CreateUserRequest;
import org.tarantool.models.rest.UpdateUserRequest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class TarantoolStorageService implements StorageService{

    private final TarantoolClient tarantoolClient;

    public TarantoolStorageService(TarantoolClient tarantoolClient) {
        this.tarantoolClient = tarantoolClient;
    }

    @Override
    public UserModel getUserByLogin(String login) {

        List<Object> userTuple = null;

        try {
            userTuple = (List<Object>) tarantoolClient.call("get_user_by_login", login).get().get(0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(userTuple != null) {
            UserModel user = new UserModel();
            user.setUuid((String)userTuple.get(1));
            user.setLogin((String)userTuple.get(2));
            user.setPassword((String)userTuple.get(3));

            return user;
        }

        return null;
    }

    @Override
    public String createUser(CreateUserRequest request) {

        String uuid = UUID.randomUUID().toString();
        List<Object> userTuple = null;

        try {
            userTuple = (List<Object>) tarantoolClient.call("create_user",
                    uuid,
                    request.getLogin(),
                    DigestUtils.md5DigestAsHex(request.getPassword().getBytes())
            ).get();
        } catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(userTuple != null) {
            return (String) userTuple.get(0);
        }

        return null;
    }

    @Override
    public boolean updateUser(String login, UpdateUserRequest request) {

        List<Object> userTuple = null;

        try {
            userTuple = (List<Object>) tarantoolClient.call("update_user_by_login",
                    login, DigestUtils.md5DigestAsHex(request.getPassword().getBytes())
            ).get().get(0);
        } catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return userTuple != null;
    }

    @Override
    public boolean deleteUser(String login) {
        List<Object> userTuple = null;

        try {
            userTuple = (List<Object>) tarantoolClient.call("delete_user_by_login",
                    login
            ).get().get(0);
        } catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return userTuple != null;
    }
}
