package ru.yadzuka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("Test")
@RequestMapping("/v1/api")
public class Controller {

    @GetMapping("")
    public String alive() {
        return "Alive!";
    }

    @GetMapping("unsecured")
    public String unsecured() {
        return "Alive!";
    }

    @GetMapping("secured")
    public String secured() {
        return "Alive!";
    }

    @GetMapping("admin")
    public String admin() {
        return "Alive!";
    }

    @GetMapping("me")
    public String getUserName(Principal principal) {
        return principal.getName();
    }
}
