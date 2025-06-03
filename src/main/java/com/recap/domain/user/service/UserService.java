package com.recap.domain.user.service;

import com.recap.domain.university.entity.University;
import com.recap.domain.university.service.UniversityService;
import com.recap.domain.user.dto.request.UserRegisterRequest;
import com.recap.domain.user.entity.User;
import com.recap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UniversityService universityService;
    private final BCryptPasswordEncoder passwordEncoder;

    public User register(UserRegisterRequest userRegisterRequest){
        User user = User.builder()
                .userId(userRegisterRequest.userId())
                .password(passwordEncoder.encode(userRegisterRequest.password()))
                .name(userRegisterRequest.name())
                .nickname(userRegisterRequest.nickname())
                .university(universityService.findUniversityByCode(userRegisterRequest.univCode()))
                .build();
        return userRepository.save(user);
    }
    // TODO : 유저 회원가입 로직 구현
    // TODO : 유저 회원 탈퇴 로직 구현
}
// dummy change
