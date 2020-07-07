package com.space.datahub.service;

import com.space.datahub.domain.Type;
import com.space.datahub.repo.TypeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class TypeService {

    @Value("${upload.path}")
    private String uploadPath;

    private final TypeRepo typeRepository;
    private final DepartmentService departmentService;

    public TypeService(TypeRepo typeRepository, DepartmentService departmentService) {
        this.typeRepository =  typeRepository;
        this.departmentService = departmentService;
    }

    public List<Type> findAll(){
        return typeRepository.findAll();
    }

    public Type findByName(String name){
        Type type = null;
        if(name != null && !name.isEmpty()) {
            type = typeRepository.findByName(name);
            return type;
        }
        return null;
    }

    public List<Type> findByDepartmentName(String department){
        List<Type> types = null;
        if(department != null && !department.isEmpty()) {
            types = typeRepository.findByDepartmentName(department);
            return types;
        }
        return null;
    }

    public List<Type> findByDepartmentId(long id) {
        return typeRepository.findByDepartmentId(id);
    }

    public void delete(Type type){
        typeRepository.delete(type);
    }

    public Type save(String name, String department, MultipartFile image) throws IOException {
        if(findByName(name) != null)
            return null;
        else {
            Type type = new Type();
            type.setName(name);
            type.setDepartment(departmentService.findByName(department));

            if (image != null) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String typeName = type.getName().replace(" ", "-");
                typeName = typeName.replace("/", "-");

                String resultFileName = UUID.randomUUID().toString() + "." + typeName + "." + image.getOriginalFilename();
                image.transferTo(new File(uploadPath + "/" + resultFileName));

                type.setImage(resultFileName);
            }

            typeRepository.save(type);
            return type;
        }
    }
}
