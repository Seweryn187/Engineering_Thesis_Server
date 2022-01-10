package com.server.services;

import com.server.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers()
    {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }


    public void registerUser(User userData) throws Exception {
        if(checkIfUserExist(userData.getEmail()) != null){
            throw new Exception("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(userData, userEntity);
        encodePassword(userEntity, userData);
        System.out.println(userRepository.save(userEntity));
    }

    public User checkIfUserExist(String email) {
        return userRepository.findByEmail(email);
    }

    private void encodePassword(User userEntity, User userData){
        userEntity.setPassword(passwordEncoder.encode(userData.getPassword()));
    }

}
