package com.space.datahub.controller;

import com.space.datahub.domain.Type;
import com.space.datahub.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return typeService.findByName(name);
    }

    @GetMapping("/department/name")
    public List<Type> byType(@RequestParam String department){
       return typeService.findByDepartmentName(department);
    }

    @GetMapping("/department/id")
    public List<Type> byId(@RequestParam long id){
        return typeService.findByDepartmentId(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Type type){
        typeService.delete(type);
    }

    @PostMapping(headers = {"Content-Type=multipart/form-data"})
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam String department, @RequestParam MultipartFile image) throws IOException {
        return new ResponseEntity<>(typeService.save(name, department, image), HttpStatus.OK);
    }
}
