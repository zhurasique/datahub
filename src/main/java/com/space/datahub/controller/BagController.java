package com.space.datahub.controller;

import com.space.datahub.domain.Bag;
import com.space.datahub.repo.BagRepo;
import com.space.datahub.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/bag")
public class BagController {
    private final BagRepo bagRepository;
    private final UserRepo userRepository;

    @Autowired
    public BagController(BagRepo bagRepository, UserRepo userRepository) {
        this.bagRepository = bagRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/filter/user")
    public Bag getBag(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return bagRepository.findByUserUsername(username);
    }
    
    @GetMapping("/filter/name")
    public Bag byUsername(@RequestParam String name){
        Bag bag = null;
        if(name != null && !name.isEmpty()) {
            bag = bagRepository.findByName(name);
            return bag;
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Bag bag){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        if(byUsername(username) == null) {
            bag.setName(username + "_bag");
            bag.setUser(userRepository.findByUsername(username));
            bagRepository.save(bag);
            return new ResponseEntity<>(bag, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
