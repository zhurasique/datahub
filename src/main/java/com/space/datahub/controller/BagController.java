package com.space.datahub.controller;

import com.space.datahub.domain.Bag;
import com.space.datahub.service.BagService;
import com.space.datahub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bag")
public class BagController {
    private final BagService bagService;
    private final UserService userService;

    @Autowired
    public BagController(BagService bagService, UserService userService) {
        this.bagService = bagService;
        this.userService = userService;
    }

    @GetMapping
    public List<Bag> list(){
        return bagService.findAll();
    }

    @GetMapping("/user")
    public Bag getBag(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return bagService.findByUserUsername(username);
    }
    
    @GetMapping("/name")
    public Bag byUsername(@RequestParam String name){
        if(name != null && !name.isEmpty()) {
            return bagService.findByName(name);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Bag bag){
        bagService.delete(bag);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String username){
        if(byUsername(username + "_bag") == null) {
            Bag bag = new Bag();
            bag.setName(userService.findByUsername(username).getUsername() + "_bag");
            bag.setUser(userService.findByUsername(username));
            bagService.save(bag);
            return new ResponseEntity<>(bag, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(byUsername(username + "_bag"), HttpStatus.ALREADY_REPORTED);
        }
    }
}
