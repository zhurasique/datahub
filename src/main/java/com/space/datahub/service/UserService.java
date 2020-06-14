package com.space.datahub.service;

import com.space.datahub.domain.User;
import com.space.datahub.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository =  userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(@RequestBody User user){
        return userRepository.save(user);
    }
}
