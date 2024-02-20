package site.junyo.minheegame.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.junyo.minheegame.api.filter.JSONUsernamePasswordAuthenticationFilter;
import site.junyo.minheegame.api.filter.handler.LogoutSuccessHandlerImpl;
import site.junyo.minheegame.api.http.util.HttpServletUtil;
import site.junyo.minheegame.jwt.JwtService;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final JwtService jwtService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final HttpServletUtil httpServletUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/", "/api/v1/**").permitAll()
                                .anyRequest().authenticated())

                .addFilterAt(
                        new JSONUsernamePasswordAuthenticationFilter(
                                authenticationManager(this.authenticationConfiguration),
                                this.httpServletUtil,
                                this.jwtService),
                        UsernamePasswordAuthenticationFilter.class)

                .logout((logout) -> logout
                        .logoutUrl("/api/v1/logout")
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl(httpServletUtil)))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico", "/error");
    }

}
