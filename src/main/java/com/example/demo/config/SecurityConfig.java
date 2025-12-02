package com.example.demo.config;


import com.example.demo.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AppUserService appUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/services", "/about", "/reseñas", "/registro", "/login","/service","/contacto","/equipo"
                                , "/room", "/reservas", "/reseñas","/account","/login**").permitAll()
                        .requestMatchers("assets/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form

                        .loginPage("/login")
                        .failureHandler(customLogin)
                        .successHandler(customLoginSuccess)
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                )
                .logout(config -> config
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .userDetailsService(appUserService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomLoginFail customLogin;
    @Autowired
    private CustomLoginSuccess customLoginSuccess;
}
