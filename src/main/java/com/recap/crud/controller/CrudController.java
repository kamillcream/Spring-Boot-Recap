package com.recap.crud.controller;

import com.recap.crud.service.MongoCrudService;
import com.recap.crud.service.MySQLCrudService;
import com.recap.crud.service.RedisCrudService;
import com.recap.domain.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crud")
public class CrudController {

    private final MySQLCrudService mysqlCrudService;
    private final MongoCrudService mongoCrudService;
    private final RedisCrudService redisCrudService;

    @PostMapping
    public Object postUser(@RequestBody RegisterRequest request){
        if (request.type().equals("mysql")){
            return mysqlCrudService.createUser(request.name(), request.userId(), request.password());
        }
        else if (request.type().equals("mongo")) {
            return mongoCrudService.createUser(request.name(), request.userId(), request.password());
        }
        else{
            return redisCrudService.createUser(request.name(), request.userId(), request.password());
        }
    }
    @GetMapping
    public Object getUser(@RequestParam String type, @RequestParam String id){
        if (type.equals("mysql")){
            return mysqlCrudService.readUserById(Integer.parseInt(id));
        }
        else if (type.equals("mongo")){
            return mongoCrudService.readUserById(id);
        }
        else {
            return redisCrudService.readUserById(id);
        }
    }
    @PutMapping
    public Object putUser(@RequestParam String type, @RequestParam String id, @RequestParam String userId){
        if (type.equals("mysql")){
            return mysqlCrudService.updateUserId(Integer.parseInt(id), userId);
        }
        else if (type.equals("mongo")){
            return mongoCrudService.updateUserId(id, userId);
        }
        else {
            return redisCrudService.updateUserId(id, userId);
        }
    }
    @DeleteMapping
    public Object deleteUser(@RequestParam String type, @RequestParam String id){
        if (type.equals("mysql")){
            return mysqlCrudService.deleteUserById(Integer.parseInt(id));
        }
        else if (type.equals("mongo")){
            return mongoCrudService.deleteUserById(id);
        }
        else {
            return redisCrudService.deleteUserById(id);
        }
    }
}
