package com.space.datahub.service;

import com.space.datahub.domain.Bag;
import com.space.datahub.repo.BagRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BagService {
    private final BagRepo bagRepository;
    private final UserService userService;

    public BagService(BagRepo bagRepository, UserService userService) {
        this.bagRepository =  bagRepository;
        this.userService = userService;
    }

    public List<Bag> findAll(){
        return bagRepository.findAll();
    }

    public Bag findByUserUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return bagRepository.findByUserUsername(username);
    }

    public Bag findByName(String name) {
        if(name != null && !name.isEmpty()) {
            return bagRepository.findByName(name);
        }
        return null;
    }

    public void delete(Bag bag){
        bagRepository.delete(bag);
    }

    public Bag save(String username){
        if(findByName(username + "_bag") == null) {
            Bag bag = new Bag();
            bag.setName(userService.findByUsername(username).getUsername() + "_bag");
            bag.setUser(userService.findByUsername(username));
            bagRepository.save(bag);
            return bag;
        }else{
            return findByName(username + "_bag");
        }
    }
}
