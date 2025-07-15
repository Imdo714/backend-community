package com.back_community.api.user.service;

import com.back_community.api.user.domain.dto.request.JoinDto;
import com.back_community.api.user.domain.dto.request.LoginDto;
import com.back_community.api.user.domain.dto.response.LoginResponse;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.user.repository.UserRepository;
import com.back_community.global.exception.handleException.DuplicateEmailException;
import com.back_community.global.exception.handleException.NotFoundException;
import com.back_community.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public LoginResponse loginAndGenerateToken(LoginDto loginDto) {
        User user = findEmailUser(loginDto.getEmail());
        matchPassword(loginDto, user);

        return LoginResponse.builder()
                .refreshToken(jwtTokenProvider.createToken(loginDto.getEmail(), user.getUserId()))
                .build();
    }

    @Override
    public void join(JoinDto joinDto) {
        existsByEmail(joinDto);
        User user = User.createUserBuilder(joinDto, bCryptPasswordEncoder.encode(joinDto.getPassword()));

        userRepository.save(user);
    }

    public User findEmailUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }

    public void matchPassword(LoginDto loginDto, User user) {
        if (!user.isPasswordMatch(loginDto.getPassword(), bCryptPasswordEncoder)) {
            throw new NotFoundException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void existsByEmail(JoinDto joinDto) {
        Optional<User> user = userRepository.findByEmail(joinDto.getEmail());
        if(user.isPresent()){
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
    }


}
