package services;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }


    public void addUser(User user) {
        userRepository.save(user);
    }
}
