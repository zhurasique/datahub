package com.space.datahub.service;

import com.space.datahub.domain.Bag;
import com.space.datahub.repo.BagRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BagService {
    private final BagRepo bagRepository;

    public BagService(BagRepo bagRepository) {
        this.bagRepository =  bagRepository;
    }

    public Bag findByUserUsername(String name){
        return bagRepository.findByUserUsername(name);
    }

    public Bag findByName(String name) {
        return bagRepository.findByName(name);
    }

    public void delete(Bag bag){
        bagRepository.delete(bag);
    }

    public Bag save(@RequestBody Bag bag){
        return bagRepository.save(bag);
    }
}
