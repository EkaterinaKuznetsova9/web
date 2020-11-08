package com.users.web.service.impl;

import com.users.web.models.User;
import com.users.web.repository.UserRepository;
import com.users.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for manipulating user data in the database (search, save, delete)
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> getByIdAsContainerObj(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        return userRepository.getUserByNameAndPassword(name, password);
    }
}
