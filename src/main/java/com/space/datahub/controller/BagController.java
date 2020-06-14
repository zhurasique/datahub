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

import javax.validation.Valid;

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
        Bag bag = null;
        if(name != null && !name.isEmpty()) {
            bag = bagService.findByName(name);
            return bag;
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Bag bag){
        bagService.delete(bag);
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
            bag.setUser(userService.findByUsername(username));
            bagService.save(bag);
            return new ResponseEntity<>(bag, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
