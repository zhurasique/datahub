package com.space.datahub.service;

import com.space.datahub.domain.Department;
import com.space.datahub.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public DepartmentService(DepartmentRepo departmentRepository) {
        this.departmentRepository =  departmentRepository;
    }

    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    public Department findByName(String name){
        if(name != null && !name.isEmpty())
            return departmentRepository.findByName(name);
        return null;
    }

    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    public Department save(String name, MultipartFile image) throws IOException {
        if(findByName(name) != null)
            return null;
        else {
            Department department = new Department();
            department.setName(name);

            if(image != null){
                File uploadDir = new File(uploadPath);

                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String departmentName = department.getName().replace(" ", "-");
                departmentName = departmentName.replace("/", "-");

                String resultFileName = UUID.randomUUID().toString() + "." +  departmentName + "." + image.getOriginalFilename();
                image.transferTo(new File(uploadPath + "/" + resultFileName));

                department.setImage(resultFileName);
            }

            departmentRepository.save(department);
            return department;
        }
    }
}
