package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    public Kitchen create(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    public List<Kitchen> findAll(){
        return kitchenRepository.findAll();
    }

    public Kitchen findById(Long id){
        return kitchenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kitchen does not exist."));
    }

    public Kitchen update(Kitchen kitchen, Long id){
        Kitchen kitchenActual = findById(id);
        kitchen.setId(kitchenActual.getId());
        return kitchenRepository.save(kitchen);
    }

    public void delete(Long id){
        try {
            Kitchen kitchenActual = findById(id);
            kitchenRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Entity not found");
        }catch (DataIntegrityViolationException ex){
            throw new EntityInUseException(String.format("Code kitchen %d cannot be deleted because it is in use.",id));
        }
    }
}
