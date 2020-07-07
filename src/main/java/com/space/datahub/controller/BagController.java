package com.space.datahub.controller;

import com.space.datahub.domain.Bag;
import com.space.datahub.service.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bag")
public class BagController {
    private final BagService bagService;

    @Autowired
    public BagController(BagService bagService) {
        this.bagService = bagService;
    }

    @GetMapping
    public List<Bag> list(){
        return bagService.findAll();
    }

    @GetMapping("/user")
    public Bag getBag(){
        return bagService.findByUserUsername();
    }
    
    @GetMapping("/name")
    public Bag byUsername(@RequestParam String name){
        return bagService.findByName(name);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Bag bag){
        bagService.delete(bag);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String username){
        return new ResponseEntity<>(bagService.save(username), HttpStatus.OK);
    }
}
