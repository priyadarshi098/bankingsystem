package com.bank.userservice.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.userservice.dto.LogInDto;
import com.bank.userservice.dto.LoginResponseDto;
import com.bank.userservice.dto.UserDto;
import com.bank.userservice.service.AuthenticationService;
import com.bank.userservice.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        UserDto registeredUserDto = userService.registerUser(userDto);
        return ResponseEntity.ok(registeredUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogInDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/loginr")
    public ResponseEntity<LoginResponseDto> loginRefresh(
        @RequestBody LogInDto loginDto, 
        HttpServletRequest request,
        HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.loginr(loginDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }


     @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }

}
