package ru.yadzuka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yadzuka.dtos.JwtRequest;
import ru.yadzuka.dtos.RegistrationDto;
import ru.yadzuka.services.AuthorizationService;

@RestController("Auth")
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        return authorizationService.authorize(jwtRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody RegistrationDto registrationDto) {
        return authorizationService.registerUser(registrationDto);
    }
}
