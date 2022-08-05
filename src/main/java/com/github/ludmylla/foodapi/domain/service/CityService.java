package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.dtos.exceptions.CityNotFoundException;
import com.github.ludmylla.foodapi.domain.dtos.exceptions.EntityInUseException;
import com.github.ludmylla.foodapi.domain.model.City;
import com.github.ludmylla.foodapi.domain.model.State;
import com.github.ludmylla.foodapi.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private static final String MSG_CITY_NOT_FOUND= "Code state %d cannot be deleted because it is in use.";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    @Transactional
    public City create(City city){
        verifyIfStateExist(city);
        return cityRepository.save(city);
    }

    public List<City> findAll(){
        return cityRepository.findAll();
    }

    public City findById(Long id){
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }

    @Transactional
    public City update(Long id, City city){
        City cityActual = findById(id);
        verifyIfStateExist(city);
        city.setId(cityActual.getId());

        return cityRepository.save(city);
    }

    @Transactional
    public void delete(Long id){
       try {
           findById(id);
           cityRepository.deleteById(id);
           cityRepository.flush();
       }catch (EmptyResultDataAccessException e){
           throw new CityNotFoundException(id);
       }catch (DataIntegrityViolationException ex){
           throw new EntityInUseException(String.format(MSG_CITY_NOT_FOUND,id));
       }
    }

    private City verifyIfStateExist(City city){
        Long idState = city.getState().getId();
        State state = stateService.findById(idState);
        city.setState(state);
        return city;
    }

}
