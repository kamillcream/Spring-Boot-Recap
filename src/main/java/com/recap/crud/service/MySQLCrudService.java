package com.recap.crud.service;

import com.recap.domain.entity.MySQLUser;
import com.recap.domain.repository.MySQLUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class MySQLCrudService{

    private final MySQLUserRepository userRepository;

    public MySQLUser createUser(String name, String userId, String password) {
        MySQLUser user = MySQLUser.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .build();
        userRepository.save(user);
        return user;
    }

    public MySQLUser readUserById(int id) {
        MySQLUser user = findUserOrThrow((int) id);
        return user;
    }

    public MySQLUser updateUserId(int id, String userId) {
        MySQLUser user = findUserOrThrow((int) id);
        user.setUserId(userId);
        return user;
    }

    public MySQLUser deleteUserById(int id) {
        MySQLUser user = findUserOrThrow((int) id);
        userRepository.delete(user);
        return user;
    }

    public MySQLUser findUserOrThrow(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }
}
