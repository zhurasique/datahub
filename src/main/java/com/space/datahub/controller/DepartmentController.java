package com.space.datahub.controller;

import com.space.datahub.domain.Department;
import com.space.datahub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if(name != null && !name.isEmpty())
            return departmentService.findByName(name);
        return null;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Department department){
        if(byName(department.getName()) != null)
            return new ResponseEntity<>(department, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            departmentService.save(department);
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }
}
