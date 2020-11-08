package com.users.web.service;

import com.users.web.models.User;

import java.util.Optional;

/**
 * Interface of the service for working with users
 */
public interface UserService {
    Iterable<User> getAll();
    User getUserById(long id);
    void remove(User user);
    void save(User user);
    boolean existsById(long id);
    Optional<User> getByIdAsContainerObj(long id);
    User getUserByName(String name);
    User getUserByNameAndPassword(String name, String password);
}
