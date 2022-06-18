package com.example.demo.controllers;

import com.example.demo.commands.AddUserDto;
import com.example.demo.commands.UserDto;
import com.example.demo.exceptions.UserNotUpdated;
import com.example.demo.percistence.models.User;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class UserController {
    @Autowired
    private final UserService usersService;

    @Autowired
    public UserController(UserService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "This method will fetch all users in the DB")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "All the users where return from de DB", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @GetMapping("/users")
    public List<UserDto> findAllUsers() {
        return usersService.findAllUsers();
    }

    @Operation(summary = "This method will fetch all users in the DB")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "The user was successfully added to the DB", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "The server has encountered a situation it does not know how to handle", content = @Content)})
    @PostMapping("/users")
    public UserDto addUser(@RequestBody AddUserDto newUser) {
        return usersService.addUser(newUser);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        usersService.createUser(user);
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return usersService.findUserById(id);
    }

   @PutMapping("users/{id}")
    public void updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        if (id != user.getId()) {
            //throw new IllegalArgumentException("User id and id do not match");
            throw new UserNotUpdated();
        }

        usersService.evictUserCache(user.getId());
        usersService.updateUser(user);
    }

    //Optional.empty();

}
