package com.space.datahub.controller;

import com.space.datahub.domain.User;
import com.space.datahub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> list(){
        return userService.findAll();
    }

    @GetMapping("/name")
    public User byUsername(@RequestParam String username){
        User user = null;
        if(username != null && !username.isEmpty()) {
            user = userService.findByUsername(username);
            return user;
        }
        return null;
    }

    @GetMapping("/email")
    public User byEmail(@RequestParam String email){
        User user = null;
        if(email != null && !email.isEmpty()) {
            user = userService.findByEmail(email);
            return user;
        }
        return null;
    }

    @GetMapping("/logged")
    public User getLoggedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userService.findByUsername(username);
    }

    @GetMapping("/logout")
    public ModelAndView method(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ModelAndView("redirect:" + "/");
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user){
        if(byUsername(user.getUsername()) == null && byEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            user.setActive(1);
            userService.save(user);

            //sendEmail(user.getEmail(), user.getUsername());

            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void sendEmail(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Registration on sklepik.pl");
        msg.setText("Hi there, "+ username + "! \nThank you for registration at sklepik.pl");

        javaMailSender.send(msg);
    }
}
