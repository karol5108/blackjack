package sap.SecurityPlayApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sap.SecurityPlayApp.service.JpaUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return  http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/").permitAll()
                        /// permits for templates
                        .requestMatchers("/style.css").permitAll()
                        .requestMatchers("/tittle.png").permitAll()
                        .requestMatchers("/bj.png").permitAll()
                        .requestMatchers("/bg.png").permitAll()
                        .requestMatchers("/logo.png").permitAll()
                        /// permits for game actions
                        .requestMatchers("/start").hasRole("USER")
                        .requestMatchers("/get-one-player").hasRole("USER")
                        .requestMatchers("/new-game").hasRole("USER")
                        .requestMatchers("/finish-game").hasRole("USER")
                        /// permits for admin
                        .requestMatchers("/users").hasRole("ADMIN")
                        .requestMatchers("/h2-console/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin().loginPage("/login")
                            .defaultSuccessUrl("/", true)
                            .permitAll()
                            .and()
                .logout(Customizer.withDefaults())
                .csrf().disable()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
