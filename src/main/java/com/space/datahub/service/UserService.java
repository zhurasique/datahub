package com.space.datahub.service;

import com.space.datahub.domain.User;
import com.space.datahub.repo.UserRepo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserService {

    private JavaMailSender javaMailSender;

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository =  userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        User user = null;
        if(username != null && !username.isEmpty()) {
            user = userRepository.findByUsername(username);
            return user;
        }
        return null;
    }

    public User findByEmail(String email){
        User user = null;
        if(email != null && !email.isEmpty()) {
            user = userRepository.findByEmail(email);
            return user;
        }
        return null;
    }

    public User getLoggedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username);
    }

    public ModelAndView method(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ModelAndView("redirect:" + "/");
    }

    void sendEmail(String email, String username) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Registration on sklepik.pl");
        msg.setText("Hi there, "+ username + "! \nThank you for registration at sklepik.pl");

        javaMailSender.send(msg);
    }

    public User save(@RequestBody User user){
        if(findByUsername(user.getUsername()) == null && findByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            user.setActive(1);
            userRepository.save(user);

            //sendEmail(user.getEmail(), user.getUsername());

            return user;
        }else{
            return null;
        }
    }
}
