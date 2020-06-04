package com.space.datahub.controller;

import com.space.datahub.domain.User;
import com.space.datahub.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public RegistrationController(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // For testing
    @GetMapping
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping("/name")
    public User byUsername(@RequestParam String username){
        User user = null;
        if(username != null && !username.isEmpty()) {
            user = userRepository.findByUsername(username);
            return user;
        }
        return null;
    }

    @GetMapping("/email")
    public User byEmail(@RequestParam String email){
        User user = null;
        if(email != null && !email.isEmpty()) {
            user = userRepository.findByEmail(email);
            return user;
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user){
        if(byUsername(user.getUsername()) == null && byEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles("USER");
            user.setActive(1);
            userRepository.save(user);

            sendEmail(user.getEmail(), user.getUsername());

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
