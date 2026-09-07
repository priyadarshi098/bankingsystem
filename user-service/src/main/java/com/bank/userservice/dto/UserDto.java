package com.bank.userservice.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String role;

    private String email;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime dateOfBirth;

    private int age;

}
