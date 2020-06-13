package com.space.datahub.controller;

import com.space.datahub.domain.Type;
import com.space.datahub.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/type")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<Type> list(){
        return typeService.findAll();
    }

    @GetMapping("/name")
    public Type byName(@RequestParam String name){
        Type type = null;
        if(name != null && !name.isEmpty()) {
            type = typeService.findByName(name);
            return type;
        }
        return null;
    }

    @GetMapping("/department/name")
    public List<Type> byType(@RequestParam String department){
        List<Type> types = null;
        if(department != null && !department.isEmpty()) {
            types = typeService.findByDepartmentName(department);
            return types;
        }
        return null;
    }

    @GetMapping("/department/id")
    public List<Type> byId(@RequestParam long id){
        return typeService.findByDepartmentId(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Type type){
        typeService.delete(type);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Type type){
        if(byName(type.getName()) != null)
            return new ResponseEntity<>(type, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            typeService.save(type);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }
}
