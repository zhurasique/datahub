package com.space.datahub.controller;

import com.space.datahub.domain.Department;
import com.space.datahub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Value("${upload.path}")
    private String uploadPath;

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
    public ResponseEntity<?> create(@RequestParam String name,
                                    @RequestParam MultipartFile image) throws IOException {
        if(byName(name) != null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            Department department = new Department();
            department.setName(name);

            if(image != null){
                File uploadDir = new File(uploadPath);

                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String resultFileName = UUID.randomUUID().toString() + "." + department.getName().replace(" ", "-") + "." + image.getOriginalFilename();
                image.transferTo(new File(uploadPath + "/" + resultFileName));

                department.setImage(resultFileName);
            }

            departmentService.save(department);
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }
}
