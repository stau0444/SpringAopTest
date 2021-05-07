package com.example.aop.controller;

import com.example.aop.dto.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class RestAPIController {

    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id , @RequestParam String name){
        return id+" "+name;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user){
        System.out.println("post method:" + user);
        return user;
    }
}
