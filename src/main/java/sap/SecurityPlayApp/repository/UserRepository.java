package sap.SecurityPlayApp.repository;

import org.springframework.data.repository.CrudRepository;
import sap.SecurityPlayApp.model.User;
import sap.SecurityPlayApp.model.UserRegistrationDto;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User save(UserRegistrationDto userRegistrationDto);


    boolean existsByUsername(String username);
}
