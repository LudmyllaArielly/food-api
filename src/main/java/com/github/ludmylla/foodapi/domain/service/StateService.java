package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import com.github.ludmylla.foodapi.domain.model.State;
import com.github.ludmylla.foodapi.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public State create(State state){
        return stateRepository.save(state);
    }

    public List<State> findAll(){
        return stateRepository.findAll();
    }

    public State findById(Long id){
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State does not exist."));
    }

    public State update(Long id, State state){
        State stateActual = findById(id);
        state.setId(stateActual.getId());
        return stateRepository.save(state);
    }

    public void delete(Long id){
       try {
           State stateActual = findById(id);
           stateRepository.deleteById(id);
       }catch (EmptyResultDataAccessException e){
           throw new EntityNotFoundException("State not found");
       }catch (DataIntegrityViolationException ex){
           throw new EntityInUseException(String.format("Code state %d cannot be deleted because it is in use.",id));
       }
    }

}
