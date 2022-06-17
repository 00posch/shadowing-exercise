package com.example.demo.services;

import com.example.demo.commands.AddUserDto;
import com.example.demo.commands.UserDto;
import com.example.demo.configs.ProfileManager;
import com.example.demo.converters.UserConverter;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UsersNotFoundException;
import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserJpaRepository;
import com.example.demo.percistence.repository.UserRepository;
import com.example.demo.percistence.repository.UserRepositoryStaticImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private String activeProfile;
    private ProfileManager profile;
    private UserRepositoryStaticImpl userRepositoryStatic;
    @Autowired
    private ConfigurableEnvironment env;
    private UserJpaRepository userJpaRepository;
    private UserRepository userRepository;

    private final UserConverter userConverter;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserDto addUser(AddUserDto addUserDto) {
        User user = userConverter.convertUserToEntity(addUserDto);
        userRepository.saveUser(user);
        return userConverter.convertUserToDto(user);
        //TODO this service method does not pass because of the userConverter that keeps returning id and username null!
    }

    public void createUser(User user) {
        userRepository.saveUser(user);
    }

    public List<UserDto> findAllUsers() {
        if (userRepository.getAllUsers().isEmpty()) {
            throw new UsersNotFoundException();
        }

        return userRepository.getAllUsers().stream()
                .map(userConverter::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Cacheable("user")
    public User findUserById(Integer id) throws UserNotFoundException {
        logger.info("get user " + id);
        Optional<User> user = userRepository.getUserById(id);

            if (user.isEmpty()) {
                logger.error("404 User ot found " + id);
                throw new UserNotFoundException();
            }

        return user.get();
    }

    @CacheEvict("user")
    public void evictUserCache(Integer id) {
        logger.info("cache evict " + id);
    }

    public void updateUser(User user) {
        if (userRepository.getUserById(user.getId()).isEmpty()) {
            throw new UserNotFoundException();
        }
         userRepository.saveUser(user);
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /*public User setUserInStaticRepo(User user) {
        userRepositoryStatic.updateUserInStaticRepository(user.getId());
        return user;
    }

    public List<User> getUserByIdInStaticRepo(Integer id) {
        return userRepositoryStatic.getUserByIdInStaticRepository(id);
    }

    public List<User> addUserInStaticRepo(User user) {
        return userRepositoryStatic.addUserToStaticRepository(user);
    }

    public List<User> getAllUsersInStaticRepo() {
        return userRepositoryStatic.getAllUsersInStaticRepository();
    }*/

}
