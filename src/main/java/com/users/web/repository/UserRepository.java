package com.users.web.repository;

import com.users.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface for working with user data
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userName = :userName and u.password = :password")
    User getUserByNameAndPassword(@Param("userName") String name, @Param("password") String password);
}

