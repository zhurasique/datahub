package com.space.datahub.controller;

import com.space.datahub.domain.Type;
import com.space.datahub.repo.TypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/type")
public class TypeController {
    private final TypeRepo typeRepo;

    @Autowired
    public TypeController(TypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }

    @GetMapping
    public List<Type> list(){
        return typeRepo.findAll();
    }

    @GetMapping("/name")
    public Type byName(@RequestParam String name){
        Type type = null;
        if(name != null && !name.isEmpty()) {
            type = typeRepo.findByName(name);
            return type;
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Type type){
        if(byName(type.getName()) != null)
            return new ResponseEntity<>(type, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            typeRepo.save(type);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }
}
