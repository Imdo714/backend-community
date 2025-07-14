package com.back_community.global.config;

import com.back_community.global.jwt.JwtAuthenticationFilter;
import com.back_community.global.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, CorsConfig corsConfig) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/wake-up-log").permitAll()
                        .requestMatchers("/", "/login", "/join", "/docs/**", "index.html").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(corsConfig.corsFitter(), ChannelProcessingFilter.class) // ChannelProcessingFilter 실행 전에 CORS 부터 검증
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
