package com.recap.crud.controller;

import com.recap.crud.service.MySQLCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crud")
public class CrudController {

    private final MySQLCrudService mysqlCrudService;

    @PostMapping
    public Object postUser(@RequestParam String type, @RequestParam String userId, @RequestParam String name, @RequestParam String password){
         return mysqlCrudService.createUser(name, userId, password);
    }
}
