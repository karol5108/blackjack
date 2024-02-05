package sap.SecurityPlayApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import sap.SecurityPlayApp.model.BlackJack;
import sap.SecurityPlayApp.model.Deck;
import sap.SecurityPlayApp.model.PlayerBJ;
import sap.SecurityPlayApp.model.User;
import sap.SecurityPlayApp.repository.UserRepository;

import java.io.IOException;

@SpringBootApplication
public class SecurityPlayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityPlayAppApplication.class, args);

			Deck deck = new Deck("bj");
			PlayerBJ player = new PlayerBJ();
			PlayerBJ hustler = new PlayerBJ();

			BlackJack blackJack = new BlackJack();
			blackJack.setDeck(deck);
			blackJack.setPlayer(player);
			blackJack.setHustler(hustler);

			blackJack.startGame();
			blackJack.getOne();
			blackJack.getOneHustler();
			System.out.println( blackJack.checkWinner());




	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder){
		User user = new User();
			user.setUsername("user");
			user.setPassword(encoder.encode("123"));
			user.setRoles("ROLE_USER");
		User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(encoder.encode("admin"));
			admin.setRoles("ROLE_ADMIN,ROLE_USER");
		return args -> {
			userRepository.save(admin);
			userRepository.save(user);
		};	}


}
