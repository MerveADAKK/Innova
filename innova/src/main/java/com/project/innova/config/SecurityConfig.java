package com.project.innova.config;

import com.project.innova.layer.business.dto.PersonDto;
import com.project.innova.layer.business.service.PersonService;
import com.project.innova.layer.business.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PersonServiceImpl service;
    /*
    @Bean
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("password"))
                .roles("USER");
    }*/

    @Bean
    public UserDetailsManager userDetailsManager(PasswordEncoder encoder){
        List<UserDetails> users = new ArrayList<>();
        List<PersonDto> allPersons = service.getAllPersons();
        if(allPersons!=null && allPersons.size()!=0) {
            for (PersonDto person : allPersons) {
                System.out.println(person.getUsername()+" "+person.getPassword());
                users.add(new User(
                        person.getUsername(),
                        encoder.encode(person.getPassword()),
                        new ArrayList<>())
                );
            }
        }
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(users);
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        //.anyRequest().authenticated()
                )
                .formLogin((log) -> log
                        .defaultSuccessUrl("/main").permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
