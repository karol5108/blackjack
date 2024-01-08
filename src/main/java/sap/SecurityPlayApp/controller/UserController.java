package sap.SecurityPlayApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sap.SecurityPlayApp.model.User;
import sap.SecurityPlayApp.model.UserRegistrationDto;
import sap.SecurityPlayApp.repository.UserRepository;
import sap.SecurityPlayApp.service.JpaUserDetailsService;

import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller

public class UserController {
    @Autowired
    JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    UserRepository userRepository;





    @PostMapping("/try-save")
    public String trySaveregisterUser() {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User trysave = new User(5L, "karol", passwordEncoder.encode("123"), "ROLE_USER");
            userRepository.save(trysave);

        } catch (Exception e) {

            e.printStackTrace(); // Możesz użyć loggera zamiast tego
            // Obsłuż błąd, na przykład, zwróć odpowiedni komunikat błędu
            return "redirect:/error";
        }
        return "redirect:/users";
    }

//    @PostMapping("/register")
//    public String registerUser(@RequestBody Map<String, String> request) {
//        System.out.println(request.get("username")+" . "+ request.get("password"));
//        try {
//            // Obsługa rejestracji użytkownika
//            jpaUserDetailsService.registerUser(request.get("username"), request.get("password"));
//            System.out.println(request.get("username")+" . "+request.get("password"));
//            return "redirect:/users";
//        } catch (IllegalArgumentException e) {
//            return "register_page";
//        }
//    }
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register_page";
    }

    @PostMapping("/register")
    public String regsitration(@ModelAttribute("user")UserRegistrationDto userRegistrationDto){
        jpaUserDetailsService.save(userRegistrationDto);
        return "redirect:?success";
    }


    private boolean isPasswordValid(String password) {
        return password.length() >= 3;
    }
}



//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/admin")
//    public String helloAdmin(){
//        return "hello admin";
//    }
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping("/user")
//    public String helloUser(){
//        return "hello user";
//    }


