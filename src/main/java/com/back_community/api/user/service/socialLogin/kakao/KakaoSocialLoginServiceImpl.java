package com.back_community.api.user.service.socialLogin.kakao;

import com.back_community.api.common.util.PasswordEncoderUtil;
import com.back_community.api.user.domain.dto.request.AccessTokenDto;
import com.back_community.api.user.domain.dto.request.KakaoUserDto;
import com.back_community.api.user.domain.dto.response.KakaoUserResponse;
import com.back_community.api.user.domain.dto.response.LoginResponse;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.user.repository.UserRepository;
import com.back_community.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoSocialLoginServiceImpl implements KakaoSocialLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Value("${CLIENT_ID}")
    private String client_id;

    @Value("${REDIRECT_URL}")
    private String redirect_url;

    @Override
    public LoginResponse kakaoSocialLogin(String code) {
        AccessTokenDto kakaoAccessToken = getKakaoAccessToken(code);
        KakaoUserDto userInfo = getUserInfoFromKakao(kakaoAccessToken.getAccessToken());

        User user = registerOrLogin(userInfo);

        return LoginResponse.builder()
                .accessToken(jwtTokenProvider.createToken(user.getEmail(), user.getUserId()))
                .build();
    }

    public AccessTokenDto getKakaoAccessToken(String code) {
        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_url);
        params.add("code", code);

        ResponseEntity<AccessTokenDto> response = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(params)
                .retrieve()
                .toEntity(AccessTokenDto.class);

        return response.getBody();
    }

    public KakaoUserDto getUserInfoFromKakao(String accessToken) {
        RestClient restClient = RestClient.create();

        KakaoUserResponse response = restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(KakaoUserResponse.class);

        String email = response.getKakaoAccount().getEmail();
        if (email == null) email = "TEST";

        String nickname = response.getKakaoAccount().getProfile().getNickname();

        return new KakaoUserDto(email, nickname);
    }

    public User registerOrLogin(KakaoUserDto kakaoUser) {
        User user = userRepository.findByEmail(kakaoUser.getEmail())
                .orElseGet(() -> {
                    User newUser = User.createKakaoUserBuilder(kakaoUser, PasswordEncoderUtil.encode(UUID.randomUUID().toString()));
                    return userRepository.save(newUser);
                });
        return user;
    }
}
