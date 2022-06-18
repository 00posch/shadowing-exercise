package com.example.demo.percistence.repository;

import com.example.demo.exceptions.UserNotUpdated;
import com.example.demo.percistence.models.User;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("qa")
public class UserRepositoryStaticImpl implements UserRepository{
    private User user;
    private List<User> usersList = new ArrayList<>();

    public UserRepositoryStaticImpl() {
        User user1 = new User(1, "userOne");
        User user2 = new User(2, "userTwo");
        User user3 = new User(3, "userThree");
        this.usersList.add(user1);
        this.usersList.add(user2);
        this.usersList.add(user3);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        for (User user : usersList) {
            if (ObjectUtils.nullSafeEquals(user.getId(), id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return usersList;

    }

    @Override
    public void saveUser(User usr) {
        usersList.add(usr);
    }

    @Override
    public void setUser(User user) {
        if (getUserById(user.getId()).isPresent()) {
            usersList.add(user);
            System.out.println(usersList);
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