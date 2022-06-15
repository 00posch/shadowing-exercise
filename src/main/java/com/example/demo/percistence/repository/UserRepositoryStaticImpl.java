package com.example.demo.percistence.repository;

import com.example.demo.exceptions.UserNotUpdated;
import com.example.demo.percistence.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryStaticImpl implements UserRepository{
    private List<User> usersList;
    private User user;

    public UserRepositoryStaticImpl(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return usersList.stream()
                .filter(user1 -> user.getId().equals(id)).findAny();
    }

    @Override
    public List<User> getAllUsers() {
        return usersList;
    }

    @Override
    public void saveUser(User usr) {
        this.user = usr;
        usersList.add(usr);
    }

    @Override
    public void setUser(User user) {
        if (getUserById(user.getId()).isPresent()) {
            usersList.add(user);
        } else {
            throw new UserNotUpdated();
        }
    }

    /////////////////////////////////////////////////////////////////////////

    /*public List<User> getAllUsersInStaticRepository() {
        return usersList;
    }

    public List<User> getUserByIdInStaticRepository(Integer id) {
        return usersList.stream()
                .filter(user1 -> user.getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<User> addUserToStaticRepository(User user) {
        usersList.add(user);
        return usersList;
    }

    public void updateUserInStaticRepository(Integer id) {
        if (!getUserByIdInStaticRepository(id).isEmpty()) {
            usersList.add(user);
        } else {
            throw new UserNotUpdated();
        }
    }*/
}