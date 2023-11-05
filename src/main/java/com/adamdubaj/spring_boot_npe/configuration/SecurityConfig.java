package com.adamdubaj.spring_boot_npe.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@RequiredArgsConstructor
public class SecurityConfig {

    //private final DataSource dataSource;
    private final ObjectMapper objectMapper;
    private final RestAuthenticationSuccessHandler successHandler;
    private final RestAuthenticationFailureHandler failureHandler;


    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }


    @Bean
    UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.builder()
                .username("test")
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("test1"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        users.createUser(admin);
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
    */

        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui.html")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("swagger-resources/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**/**")).permitAll()
                            .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                //.addFilter(authenticationFilter());
                .formLogin(Customizer.withDefaults())
                .headers(Customizer.withDefaults());
        return http.build();
     }

     public JsonObjectAuthenticationFilter authenticationFilter() throws Exception{
        JsonObjectAuthenticationFilter authenticationFilter = new JsonObjectAuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        //authenticationFilter.setAuthenticationManager();
        return authenticationFilter;
     }
}