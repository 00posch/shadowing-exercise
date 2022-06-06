package com.example.demo.commands;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserDto {
    @NotBlank()
    private String username;

}
