package com.example.demo.percistence.repository;

import com.example.demo.percistence.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> getUserById(Integer id);
    List<User> getAllUsers();
    void saveUser(User user);
    void setUser(User user);
}
