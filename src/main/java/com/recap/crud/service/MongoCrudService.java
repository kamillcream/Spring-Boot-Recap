package com.recap.crud.service;


import com.recap.domain.entity.MongoUser;
import com.recap.domain.repository.MongoUserRepository;
import com.recap.domain.repository.MongoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MongoCrudService{

    private final MongoUserRepository userRepository;

    @Transactional
    public MongoUser createUser(String name, String userId, String password) {
        MongoUser user = MongoUser.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .build();
        userRepository.save(user);
        return user;
    }

    public MongoUser readUserById(String id) {
        MongoUser user = findUserOrThrow(id);
        return user;
    }

    @Transactional
    public MongoUser updateUserId(String id, String userId) {
        MongoUser user = findUserOrThrow(id);
        user.setUserId(userId);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public MongoUser deleteUserById(String id) {
        MongoUser user = findUserOrThrow(id);
        userRepository.delete(user);
        return user;
    }

    public MongoUser findUserOrThrow(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }
}
