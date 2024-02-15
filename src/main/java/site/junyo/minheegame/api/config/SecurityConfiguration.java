package site.junyo.minheegame.api.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(authorizeHttpRequests -> {
            authorizeHttpRequests
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .requestMatchers("/", "/api/v1/sign-up").permitAll()
                    .anyRequest().authenticated();

        }).csrf(AbstractHttpConfigurer::disable).build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico", "/error");
    }

}
