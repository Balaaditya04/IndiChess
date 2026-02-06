package com.example.user.config;

import com.example.user.filters.JwtFilter;
import com.example.user.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService userDetailService;
    private final JwtFilter jwtFilter;
    private final CustomOAuth2SuccessHandler oauth2SuccessHandler;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // user details service
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain newSpringSecurityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login",  "/auth/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
        )
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session ->
                session.sessionCreationPolicy
                        (SessionCreationPolicy.STATELESS))
        .oauth2Login(oauth2 -> oauth2
                .successHandler(oauth2SuccessHandler)
        )
        .httpBasic(Customizer.withDefaults())
        .build();  // BUILD THE SECURITY SYSTEM!
//        return http
//                .csrf(csrf -> csrf.disable())
//
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
////                .securityContext(sc -> sc.requireExplicitSave(false)) // optional, safe
////                .requestCache(rc -> rc.disable())                     // avoids saving requests in session
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/hello", "/auth/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//
////                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
    }




}

