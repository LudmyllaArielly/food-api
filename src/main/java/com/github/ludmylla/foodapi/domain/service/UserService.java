package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.User;
import com.github.ludmylla.foodapi.domain.repository.UserRepository;
import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User update(Long id, User user){
        User userActual = findById(id);
        user.setId(userActual.getId());
        user.setPassword(userActual.getPassword());
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(Long id, String passwordActual, String newPassword){
        User user = findById(id);

        if(user.passwordDoesNotMatch(passwordActual)){
            throw new BusinessException("Current password entered does not match the user's password.");
        }

        user.setPassword(newPassword);
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        userRepository.deleteById(id);
        userRepository.flush();
    }
}
