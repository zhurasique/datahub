package com.space.datahub.repo;

import com.space.datahub.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepo userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Delete all
        this.userRepository.deleteAll();

        // Crete users
        User user = new User("user",passwordEncoder.encode("user"),"user@pl.pl", "USER","");
        User admin = new User("admin",passwordEncoder.encode("admin"),"admin@pl.pl","ADMIN","ACCESS_TEST1,ACCESS_TEST2");

        List<User> users = Arrays.asList(user, admin);

        // Save to db
        this.userRepository.saveAll(users);
    }
}
