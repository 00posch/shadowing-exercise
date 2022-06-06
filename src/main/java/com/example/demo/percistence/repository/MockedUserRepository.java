package com.example.demo.percistence.repository;

import com.example.demo.commands.AddUserDto;
import com.example.demo.commands.UserDto;
import com.example.demo.percistence.models.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MockedUserRepository {

    UserDto CreateUser(AddUserDto newUser);
    List<User> getAllUsers();
}
