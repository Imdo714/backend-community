package com.back_community.api.user.service.socialLogin.google;

import com.back_community.api.common.util.PasswordEncoderUtil;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.user.repository.UserRepository;
import com.back_community.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleSocialLoginServiceImpl implements GoogleSocialLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public String googleSocialLogin(OAuth2User oAuth2User) {
        User user = googleRegisterOrLogin(oAuth2User);
        return jwtTokenProvider.createToken(user.getEmail(), user.getUserId());
    }

    public User googleRegisterOrLogin(OAuth2User oAuth2User) {
        User user = userRepository.findByEmail(oAuth2User.getAttribute("email"))
                .orElseGet(() -> {
                    User newUser = User.createGoogleUserBuilder(oAuth2User, PasswordEncoderUtil.encode(oAuth2User.getAttribute("sub")));
                    return userRepository.save(newUser);
                });
        return user;
    }

}
