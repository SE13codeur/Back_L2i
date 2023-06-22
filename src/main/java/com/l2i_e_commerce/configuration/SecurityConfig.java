package com.l2i_e_commerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager users(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
                .passwordEncoder(encoder()).and().build();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET, "/auth").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/books").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/books/new").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/books/promos").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/books/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/orders").permitAll()
                                .requestMatchers(HttpMethod.POST, "/items/orders").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/orders/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/items/orders/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/items/orderlines/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/account/user/profile").permitAll()
                                .requestMatchers(HttpMethod.GET, "/account/user/favorites").permitAll()
                                .requestMatchers(HttpMethod.POST, "/items/payment").permitAll()
                                .requestMatchers(HttpMethod.GET, "/admin/items/books").permitAll()
                                .requestMatchers(HttpMethod.GET, "/admin/orders").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/admin/orders/{id}").permitAll()
                                .anyRequest().authenticated()
                )


/*
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
*/

                .httpBasic(Customizer.withDefaults())
                .build();
    }


/*@Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(RSAPublicKey publicKey) {
        JWK jwk = new RSAKey.Builder(publicKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public RSAPublicKey rsaPublicKey() {
        try {
            return KeyLoader.loadPublicKey("/jwt/public.pem");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey() {
        try {
            return KeyLoader.loadPrivateKey("/jwt/private.pem");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

}

