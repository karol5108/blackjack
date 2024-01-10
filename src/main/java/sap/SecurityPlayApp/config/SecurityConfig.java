package sap.SecurityPlayApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import sap.SecurityPlayApp.service.JpaUserDetailsService;

import java.util.HashSet;
import java.util.Set;

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
                        .requestMatchers("/loginGithub").permitAll()
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
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/loginGithub")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults())
                .csrf().disable()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /// dodanie roli USER dla uzytkownika git
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new CustomOAuth2UserService();
    }
    private static class CustomOAuth2UserService extends DefaultOAuth2UserService {

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2User user = super.loadUser(userRequest);

            // Tutaj możesz sprawdzić informacje o użytkowniku i nadać mu role
            Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
            System.out.println(user.getName());


            authorities.add(new OAuth2UserAuthority("ROLE_USER", user.getAttributes()));
            System.out.println(authorities);

            return new DefaultOAuth2User(authorities, user.getAttributes(), "login");
        }
    }
}

