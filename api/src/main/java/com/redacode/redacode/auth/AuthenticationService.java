package com.redacode.redacode.auth;

import com.redacode.redacode.config.JwtService;
import com.redacode.redacode.model.Role;
import com.redacode.redacode.model.User;
import com.redacode.redacode.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final  UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(User userRequest) {
        User user = User.builder().firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName()).email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword())).role(Role.STUDENT_MANAGER).build();
        userRepo.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest userRequest) {
         /*the authentication manager bean has a method authenticate which allow us to authenticate a user
          based on the username and the password, so this authenticateMethod takes an object of type username
          password authentication token*/
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getEmail(),
                userRequest.getPassword()
        ));
        // if the user is authenticated
        User user = userRepo.findByEmail(userRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
