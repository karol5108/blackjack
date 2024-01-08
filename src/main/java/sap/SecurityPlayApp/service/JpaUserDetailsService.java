package sap.SecurityPlayApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sap.SecurityPlayApp.model.UserSecurity;
import sap.SecurityPlayApp.model.User;
import sap.SecurityPlayApp.model.UserRegistrationDto;
import sap.SecurityPlayApp.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository
                .findByUsername(username)
                .map(UserSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username is not found " + username));
    }

    public void registerUser(String username, String password) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("rozpoczeto rejestracje");
        if (isPasswordValid(password)) {
            if (!userRepository.existsByUsername(username)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setRoles("ROLE_USER");
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Użytkownik o podanym loginie już istnieje.");
            }
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane rejestracyjne. Sprawdź, czy login ma co najmniej 3 znaki, a hasło ma co najmniej 3 znaki.");
        }
    }

    public User save(UserRegistrationDto userRegistrationDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
                user.setUsername(userRegistrationDto.getUsername());
                user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
                user.setRoles("ROLE_USER");
        return  userRepository.save(user);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 3;
    }

}

