package com.server.controllers;

import com.server.entities.User;
import com.server.request.SignInRequest;
import com.server.security.UserDetailsImpl;
import com.server.security.jwt.JwtUtils;
import com.server.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.server.services.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping("/user/register")
    public Map<String, String> registerUser(final @RequestBody @Valid User userData, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return Collections.singletonMap("response", "sign-up");
        }
        try {
            userService.registerUser(userData);
        }catch (Exception e){
            return Collections.singletonMap("response", "sign-up");
        }
        return Collections.singletonMap("response", "sign-in");
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping("user/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getLogin(), signInRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println(jwt);

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getUsername(),
                user.getEmail()
        ));
    }
}
