package com.redacode.redacode.auth;

import com.redacode.redacode.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/springSecurity/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authService;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User userRequest)
    {
        System.out.println("hello my friend");
        return ResponseEntity.ok(authService.register(userRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest userRequest)
    {
        System.out.println("hello my friend from authentication");
        return ResponseEntity.ok(authService.authenticate(userRequest));
    }
}
