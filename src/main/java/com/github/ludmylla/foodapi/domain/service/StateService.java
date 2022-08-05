package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.dtos.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.dtos.exceptions.StateNotFoundException;
import com.github.ludmylla.foodapi.domain.model.State;
import com.github.ludmylla.foodapi.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    private static final String MSG_STATE_IN_USE = "Code state %d cannot be deleted because it is in use.";

    @Autowired
    private StateRepository stateRepository;

    @Transactional
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

    @Transactional
    public State update(Long id, State state){
        State stateActual = findById(id);
        state.setId(stateActual.getId());
        return stateRepository.save(state);
    }

    @Transactional
    public void delete(Long id){
       try {
           findById(id);
           stateRepository.deleteById(id);
           stateRepository.flush();
       }catch (EmptyResultDataAccessException e){
           throw new StateNotFoundException(id);
       }catch (DataIntegrityViolationException ex){
           throw new EntityInUseException(String.format(MSG_STATE_IN_USE,id));
       }
    }

}
