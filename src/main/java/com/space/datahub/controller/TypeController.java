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
    private final TypeRepo typeRepository;

    @Autowired
    public TypeController(TypeRepo typeRepository) {
        this.typeRepository = typeRepository;
    }

    @GetMapping
    public List<Type> list(){
        return typeRepository.findAll();
    }

    @GetMapping("/name")
    public Type byName(@RequestParam String name){
        Type type = null;
        if(name != null && !name.isEmpty()) {
            type = typeRepository.findByName(name);
            return type;
        }
        return null;
    }

    @GetMapping("/department/name")
    public Iterable<Type> byType(@RequestParam String department){
        Iterable<Type> types = null;
        if(department != null && !department.isEmpty()) {
            types = typeRepository.findByDepartmentName(department);
            return types;
        }
        return null;
    }

    @GetMapping("/department/id")
    public Iterable<Type> byId(@RequestParam long id){
        Iterable<Type> types = null;
        types = typeRepository.findByDepartmentId(id);
        return types;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Type type){
        typeRepository.delete(type);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Type type){
        if(byName(type.getName()) != null)
            return new ResponseEntity<>(type, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            typeRepository.save(type);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }
}
