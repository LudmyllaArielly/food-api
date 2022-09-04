package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.service.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.service.exceptions.KitchenNotFoundException;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.repository.KitchenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KitchenService {

    private static final String MSG_KITCHEN_IN_USE= "Code kitchen %d cannot be deleted because it is in use.";

    private KitchenRepository kitchenRepository;

    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @Transactional
    public Kitchen create(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    public Page<Kitchen> findAll(Pageable pageable){
        return kitchenRepository.findAll(pageable);
    }

    public Kitchen findById(Long id){
        return kitchenRepository.findById(id)
                .orElseThrow(() -> new KitchenNotFoundException(id));
    }

    @Transactional
    public Kitchen update(Kitchen kitchen, Long id){
        Kitchen kitchenActual = findById(id);
        kitchen.setId(kitchenActual.getId());
        return kitchenRepository.save(kitchen);
    }

    @Transactional
    public void delete(Long id){
        try {
            findById(id);
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new KitchenNotFoundException(id);
        }catch (DataIntegrityViolationException ex){
            throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE,id));
        }
    }
}
