package com.server.services;

import com.server.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.server.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Integer deleteUserByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }



    public void registerUser(User userData) throws Exception {
        if(checkIfUserExist(userData.getEmail()) != null){
            throw new Exception("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(userData, userEntity);
        encodePassword(userEntity, userData);
        log.info("Registered new user: " + userRepository.save(userEntity));
    }

    public User checkIfUserExist(String email) {
        return userRepository.findByEmail(email);
    }

    private void encodePassword(User userEntity, User userData){
        userEntity.setPassword(passwordEncoder.encode(userData.getPassword()));
    }

    public User loadUserByLogin(String login) throws UsernameNotFoundException {

        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + login));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }



}
