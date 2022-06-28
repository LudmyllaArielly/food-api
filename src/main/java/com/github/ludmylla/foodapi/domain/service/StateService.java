package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.exceptions.StateNotFoundException;
import com.github.ludmylla.foodapi.domain.model.State;
import com.github.ludmylla.foodapi.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private static final String MSG_STATE_IN_USE = "Code state %d cannot be deleted because it is in use.";

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
                .orElseThrow(() -> new StateNotFoundException(id));
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
           throw new StateNotFoundException(id);
       }catch (DataIntegrityViolationException ex){
           throw new EntityInUseException(String.format(MSG_STATE_IN_USE,id));
       }
    }

}
