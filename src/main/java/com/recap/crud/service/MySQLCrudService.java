package com.recap.crud.service;

import com.recap.global.dto.UserResponse;
import com.recap.global.entity.User;
import com.recap.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MySQLCrudService{

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(String name, String userId, String password) {
        User user = User.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .build();
        userRepository.save(user);
        return UserResponse.from(user);
    }

    public UserResponse readUserById(int id) {
        User user = findUserOrThrow(id);
        return UserResponse.from(user);
    }


    public UserResponse updateUserId(int id, String userId) {
        User user = findUserOrThrow(id);
        user.setUserId(userId);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse deleteUserById(int id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
        return UserResponse.from(user);
    }

    public User findUserOrThrow(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }
}
