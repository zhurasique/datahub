package com.space.datahub.controller;

import com.space.datahub.domain.Department;
import com.space.datahub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> list(){
        return departmentService.findAll();
    }

    @GetMapping("/name")
    public Department byName(@RequestParam String name){
        return departmentService.findByName(name);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Department department){
        departmentService.delete(department);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam String name,
                                    @RequestParam MultipartFile image) throws IOException {
        return new ResponseEntity<>(departmentService.save(name, image), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
