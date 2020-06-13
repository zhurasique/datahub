package com.space.datahub.service;

import com.space.datahub.domain.Type;
import com.space.datahub.repo.TypeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TypeService {
    private final TypeRepo typeRepository;

    public TypeService(TypeRepo typeRepository) {
        this.typeRepository =  typeRepository;
    }

    public List<Type> findAll(){
        return typeRepository.findAll();
    }

    public Type findByName(String name){
        return typeRepository.findByName(name);
    }

    public List<Type> findByDepartmentName(String name){
        return typeRepository.findByDepartmentName(name);
    }

    public List<Type> findByDepartmentId(long id) {
        return typeRepository.findByDepartmentId(id);
    }

    public void delete(Type type){
        typeRepository.delete(type);
    }

    public Type save(@RequestBody Type type){
        return typeRepository.save(type);
    }
}
