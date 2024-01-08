package sap.SecurityPlayApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sap.SecurityPlayApp.model.User;
import sap.SecurityPlayApp.repository.UserRepository;

import java.util.List;

@RestController

public class UserRestController {

    @Autowired
    UserRepository userRepository;
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }
}
