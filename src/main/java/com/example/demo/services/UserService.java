package com.example.demo.services;

import com.example.demo.commands.AddUserDto;
import com.example.demo.commands.UserDto;
import com.example.demo.converters.UserConverter;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UsersNotFoundException;
import com.example.demo.percistence.models.User;
import com.example.demo.percistence.repository.UserRepo;
import com.example.demo.percistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
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
        userRepository.save(user);
        return userConverter.convertUserToDto(user);

    }

    public List<UserDto> findAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new UsersNotFoundException();
        }

        return userRepository.findAll().stream()
                .map(userConverter::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Cacheable("user")
    public User getUser(Integer id) throws UserNotFoundException {
        logger.info("get user " + id);
        Optional<User> user = userRepository.findById(id);

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

    public User updateUser(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException();
        }
         return userRepository.save(user);
    }
}
