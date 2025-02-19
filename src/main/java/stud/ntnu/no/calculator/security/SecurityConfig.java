package stud.ntnu.no.calculator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  // inject SecurityFilterChain and tell that all requests are authenticated
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //Given security config contained deprecated methods, this is what chatGPT have as a replacement
    return http
        .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless JWT authentication
        .cors(cors -> {}) // Enable CORS with default settings
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/token").permitAll() // Allow token endpoint without authentication
            .anyRequest().authenticated() // Require authentication for all other requests
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless API
        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) // Add JWT filter
        .build();
  }
}
