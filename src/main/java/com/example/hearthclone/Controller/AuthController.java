package com.example.hearthclone.Controller;

import com.example.hearthclone.Service.AuthService;
import com.example.hearthclone.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // чтобы HTML + JS могли подключаться
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // login
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    // login
    @PostMapping("/login")
    public User login(@RequestParam String username,
                      @RequestParam String password) {
        return authService.login(username, password);
    }
}
