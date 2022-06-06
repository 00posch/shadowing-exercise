package com.example.demo.converters;

import com.example.demo.commands.AddUserDto;
import com.example.demo.commands.UserDto;
import com.example.demo.percistence.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public User convertUserToEntity(AddUserDto addUserDto) {
        return modelMapper.map(addUserDto, User.class);
    }

    public UserDto convertUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
