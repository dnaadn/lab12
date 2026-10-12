package com.example.lab12.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/get-all", "/api/v1/auth/delete/{userId}", "/api/v1/blog/get-all").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/auth/update", "/api/v1/blog/my-blogs", "/api/v1/blog/add", "/api/v1/blog/update/{blogId}",
                                "/api/v1/blog/delete/{blogId}", "/api/v1/blog/get/{blogId}", "/api/v1/blog/title/{title}").hasAuthority("USER")
                        .anyRequest().authenticated())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/api/v1/auth/logout").invalidateHttpSession(true))
                .httpBasic(httpSecurityHttpBasicConfigurer -> {}).build();
    }

}
