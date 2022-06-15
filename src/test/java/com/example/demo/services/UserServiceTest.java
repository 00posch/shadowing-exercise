package com.example.demo.services;


import com.example.demo.commands.AddUserDto;
import com.example.demo.commands.UserDto;
import com.example.demo.converters.UserConverter;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UsersNotFoundException;
import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserJpaRepository;
import com.example.demo.percistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private User user;
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private UserRepository userRepository;
    private AutoCloseable autoCloseable;
    private UserService testUserService;
    private UserConverter userConverter;
    @Mock
    private AddUserDto addUserDto;

    @BeforeEach
    void init() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userConverter = new UserConverter(new ModelMapper());
        testUserService = new UserService(userRepository, userConverter);

    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    private User mockedUser() {
        return User.builder()
                .id(1)
                .username("hello")
                .build();
    }

    private UserDto mockedUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Test
    void shouldFindAllUsers() {
        User user = mockedUser();
        Mockito.when(userRepository.getAllUsers()).thenReturn(List.of(user));
        List<UserDto> userList = testUserService.findAllUsers();
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals(user.getId(), userList.get(0).getId());
        assertEquals(user.getUsername(), userList.get(0).getUsername());
    }

    @Test
    void findAllUsersShouldReturnUsersNotFoundException() {
        Mockito.when(userRepository.getAllUsers()).thenReturn(new ArrayList<>());
        assertThrows(UsersNotFoundException.class, () -> {testUserService.findAllUsers();});
    }

    @Test
    void shouldGetUser() {
        // given
        Integer id = 1;
        User user = mockedUser();
        when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
        // then
        assertEquals(user, testUserService.findUserById(id));
    }

    /*@Test
    void shouldAddUser() {
        // given
        addUserDto = new AddUserDto("hello");
        // when
        testUserService.addUser(addUserDto);
        //then
        ArgumentCaptor<AddUserDto> argumentCaptor = ArgumentCaptor.forClass(AddUserDto.class);
        verify(userRepository).save(argumentCaptor.capture());
    }*/

    @Test
    void shouldAddUser() {
        User user = mockedUser();
        //UserDto userDto = userConverter.convertUserToDto(user);
        //Mockito.when(testUserService.addUser(addUserDto)).thenReturn(userDto);
        testUserService.createUser(user);
        Mockito.verify(userRepository).saveUser(user);
    }

    @Test
    void shouldUpdateUser() {
        User user = mockedUser();
        Mockito.when(userRepository.getUserById(user.getId())).thenReturn(Optional.of(user));
        testUserService.updateUser(user);
        Mockito.verify(userRepository).saveUser(user);
    }

    @Test
    void shoundTrhowUserNotFoundExceptionUpdatingUser() {
        User user = mockedUser();
        //Mockito.when(testUserService.updateUser(user)).thenReturn(userRepository.save(user));
        assertThrows(UserNotFoundException.class, () -> {testUserService.updateUser(user);});
    }

}