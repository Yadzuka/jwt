package ru.yadzuka.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
}
