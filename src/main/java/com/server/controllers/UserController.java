package com.server.controllers;

import com.server.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.server.services.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.rtsp.RtspMethods.REDIRECT;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping("/user/register")
    public Map<String, String> registerUser(final @RequestBody @Valid User userData, final BindingResult bindingResult, final Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userData);
            return Collections.singletonMap("response", "sign-up");
        }
        try {
            userService.registerUser(userData);
        }catch (Exception e){
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userData);
            return Collections.singletonMap("response", "sign-up");
        }
        return Collections.singletonMap("response", "sign-in");
    }
}
