package com.back_community.global.config;

import com.back_community.api.user.service.socialLogin.google.GoogleOAuth2UserService;
import com.back_community.global.config.oauth.CustomAuthenticationEntryPoint;
import com.back_community.global.config.oauth.CustomOAuth2FailureHandler;
import com.back_community.global.jwt.JwtAuthenticationFilter;
import com.back_community.global.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.back_community.global.config.SecurityWhiteList.PUBLIC_GET_URLS;
import static com.back_community.global.config.SecurityWhiteList.PUBLIC_URLS;

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
                                .requestMatchers(HttpMethod.GET, PUBLIC_GET_URLS).permitAll()
                                .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 추가
                )

                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler)
                        .failureHandler(new CustomOAuth2FailureHandler())
                )

                .addFilterBefore(corsConfig.corsFilter(), ChannelProcessingFilter.class) // ChannelProcessingFilter 실행 전에 CORS 부터 검증
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
