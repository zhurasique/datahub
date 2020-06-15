package com.space.datahub.controller;

import com.space.datahub.domain.Department;
import com.space.datahub.domain.Type;
import com.space.datahub.service.DepartmentService;
import com.space.datahub.service.TypeService;
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
@RequestMapping("api/type")
public class TypeController {

    @Value("${upload.path}")
    private String uploadPath;

    private final TypeService typeService;
    private final DepartmentService departmentService;

    @Autowired
    public TypeController(TypeService typeService, DepartmentService departmentService) {
        this.typeService = typeService;
        this.departmentService = departmentService;
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

    @PostMapping(headers = {"Content-Type=multipart/form-data"})
    public ResponseEntity<?> create(@RequestParam String name, @RequestParam String department, @RequestParam MultipartFile image) throws IOException {
        if(byName(name) != null)
            return new ResponseEntity<>(department, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            Type type = new Type();
            type.setName(name);
            type.setDepartment(findDep(department));

            if(image != null){
                File uploadDir = new File(uploadPath);

                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String resultFileName = UUID.randomUUID().toString() + "." + type.getName().replace(" ", "-") + "." + image.getOriginalFilename();
                image.transferTo(new File(uploadPath + "/" + resultFileName));

                type.setImage(resultFileName);
            }

            typeService.save(type);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }

    public Department findDep(String name){
        return departmentService.findByName(name);
    }
}
