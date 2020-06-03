package com.space.datahub.controller;

import com.space.datahub.domain.Department;
import com.space.datahub.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    private final DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentController(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @GetMapping
    public List<Department> list(){
        return departmentRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Department department){
        if(byName(department.getName()) != null)
            return new ResponseEntity<>(department, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            departmentRepo.save(department);
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }

    @GetMapping("/name")
    public Department byName(@RequestParam String name){
        Department department = null;
        if(name != null && !name.isEmpty()) {
            department = departmentRepo.findByName(name);
            return department;
        }
        return null;
    }
}
