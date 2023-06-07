package com.l2i_e_commerce;

import java.util.List;

import com.l2i_e_commerce.configuration.RsaKeyProperties;
import com.l2i_e_commerce.dao.UserRepository;
import com.l2i_e_commerce.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SpringOauthJwtApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringOauthJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {

            userRepository.saveAll(List.of(
                    User.builder().username("user").password(encoder.encode("user"))
                            .roles(List.of(new Role("USER"))).build(),
                    User.builder().username("admin").password(encoder.encode("admin"))
                            .roles(List.of(new Role("ADMIN"))).build()));
        };
    }
}
