package com.recap.crud.service;

import com.recap.domain.entity.RedisUser;
import com.recap.domain.repository.RedisUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedisCrudService {
    
    private final RedisUserRepository userRepository;

    @Transactional
    public RedisUser createUser(String name, String userId, String password) {
        RedisUser user = RedisUser.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .build();
        userRepository.save(user);
        return user;
    }

    public RedisUser readUserById(String id) {
        RedisUser user = findUserOrThrow(id);
        return user;
    }

    @Transactional
    public RedisUser updateUserId(String id, String userId) {
        RedisUser user = findUserOrThrow(id);
        user.setUserId(userId);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public RedisUser deleteUserById(String id) {
        RedisUser user = findUserOrThrow(id);
        userRepository.delete(user);
        return user;
    }

    public RedisUser findUserOrThrow(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }
}
