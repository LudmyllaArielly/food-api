package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Team;
import com.github.ludmylla.foodapi.domain.model.User;
import com.github.ludmylla.foodapi.domain.repository.UserRepository;
import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;

    @Transactional
    public User create(User user){
        checksIfTheUserEmailAlreadyExists(user);
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
        checksIfTheUserEmailAlreadyExists(user);
        user.setPassword(userActual.getPassword());
        user.setRegistrationDate(userActual.getRegistrationDate());
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

    @Transactional
    public void addTeam(Long userId, Long teamId){
        User user = findById(userId);
        Team team = teamService.findById(teamId);
        user.addTeam(team);
    }

    @Transactional
    public void removeTeam(Long userId, Long teamId){
        User user = findById(userId);
        Team team = teamService.findById(teamId);
        user.removeTeam(team);
    }

    private void checksIfTheUserEmailAlreadyExists(User user){
        Optional<User> userEmailInUser = userRepository.findByEmail(user.getEmail());

        if(userEmailInUser.isPresent() && !userEmailInUser.get().equals(user)){
            throw new BusinessException(String.format("There is already a registered user with this email.", user.getEmail()));
        }
    }

}
