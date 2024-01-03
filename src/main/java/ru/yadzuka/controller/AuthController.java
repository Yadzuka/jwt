package ru.yadzuka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yadzuka.dtos.JwtRequest;
import ru.yadzuka.dtos.JwtResponse;
import ru.yadzuka.exceptions.Error;
import ru.yadzuka.services.UserService;
import ru.yadzuka.utils.JwtTokenUtils;

@RestController("Auth")
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.UNAUTHORIZED.value(), "Not correct user or password."),
                    HttpStatus.UNAUTHORIZED
            );
        }

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = tokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
