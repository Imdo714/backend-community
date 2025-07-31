package com.back_community.global.config;

import com.back_community.api.user.service.socialLogin.google.GoogleOAuth2UserService;
import com.back_community.global.jwt.JwtAuthenticationFilter;
import com.back_community.global.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;
    private final GoogleOAuth2UserService successHandler;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, CorsConfig corsConfig, GoogleOAuth2UserService successHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsConfig = corsConfig;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/wake-up-log").permitAll()
                        .requestMatchers("/", "/login", "/join", "/docs/**", "index.html", "/kakao/login", "/login/oauth2/code/google", "/oauth2/authorization/google").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler)
                )

                .addFilterBefore(corsConfig.corsFilter(), ChannelProcessingFilter.class) // ChannelProcessingFilter 실행 전에 CORS 부터 검증
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
