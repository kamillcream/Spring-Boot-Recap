package com.recap.crud.controller;

import com.recap.crud.service.MySQLCrudService;
import com.recap.crud.service.RedisCrudService;
import com.recap.global.dto.RegisterRequest;
import com.recap.global.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crud")
public class CrudController {

    private final MySQLCrudService mysqlCrudService;
    private final RedisCrudService redisCrudService;

    @PostMapping
    public UserResponse postUser(@RequestBody RegisterRequest request){
        return mysqlCrudService.createUser(request.name(), request.userId(), request.password());
    }
    @GetMapping
    public UserResponse getUser(@PathVariable int id){
        return mysqlCrudService.readUserById(id);
    }
    @PutMapping
    public UserResponse putUser(@PathVariable int id, @PathVariable String newId){
        return mysqlCrudService.updateUserId(id, newId);
    }
    @DeleteMapping
    public UserResponse deleteUser(@PathVariable int id){
        return mysqlCrudService.deleteUserById(id);
    }
}
