package com.example.demo.percistence.repository;

import com.example.demo.exceptions.UserNotUpdated;
import com.example.demo.percistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class UserRepositoryDBImpl implements UserRepository {
    private User userEntity;
    private JpaRepository<User, Integer> jpaRepository;
    @Override
    public Optional<User> getUserById(Integer id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return jpaRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        jpaRepository.save(user);
    }

    @Override
    public void setUser(User user) {
        if (getUserById(user.getId()).isEmpty()) {
            throw new UserNotUpdated();
        }
        jpaRepository.save(user);
    }

}
