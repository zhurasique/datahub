package com.space.datahub.controller;

import com.space.datahub.domain.User;
import com.space.datahub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list(){
        return userService.findAll();
    }

    @GetMapping("/name")
    public User byUsername(@RequestParam String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/email")
    public User byEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/logged")
    public User getLoggedUser(){
        return userService.getLoggedUser();
    }

    @GetMapping("/logout")
    public ModelAndView method(HttpServletRequest request, HttpServletResponse response) {
        return userService.method(request, response);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user){
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}
