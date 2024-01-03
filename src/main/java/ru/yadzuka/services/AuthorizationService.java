package ru.yadzuka.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yadzuka.dtos.JwtRequest;
import ru.yadzuka.dtos.JwtResponse;
import ru.yadzuka.dtos.RegistrationDto;
import ru.yadzuka.dtos.UserDto;
import ru.yadzuka.exceptions.Error;
import ru.yadzuka.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserService userService;
    private final JwtTokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> authorize(@RequestBody JwtRequest jwtRequest) {
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

        var userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        var token = tokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> registerUser(RegistrationDto registrationDto) {
        if (Strings.isEmpty(registrationDto.getUsername())
                || Strings.isEmpty(registrationDto.getPassword())
                || Strings.isEmpty(registrationDto.getConfirmPassword())) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.BAD_REQUEST.value(), "Required fields are empty."),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.BAD_REQUEST.value(), "Passwords are not the same."),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (userService.getByUsername(registrationDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.BAD_REQUEST.value(), "This user already exists."),
                    HttpStatus.BAD_REQUEST
            );
        }
        final var createdUser = userService.createUser(registrationDto);
        return new ResponseEntity<>(
                new UserDto(
                        createdUser.getUsername(),
                        createdUser.getPassword(),
                        createdUser.getEmail()
                ),
                HttpStatus.OK
        );
    }
}
