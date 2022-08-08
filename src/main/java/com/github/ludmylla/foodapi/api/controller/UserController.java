package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.api.assembler.UserInputDisassembler;
import com.github.ludmylla.foodapi.api.assembler.UserModelAssembler;
import com.github.ludmylla.foodapi.domain.dtos.UserModel;
import com.github.ludmylla.foodapi.domain.dtos.input.PasswordInputModel;
import com.github.ludmylla.foodapi.domain.dtos.input.UserInputModel;
import com.github.ludmylla.foodapi.domain.dtos.input.UserWithPasswordInputModel;
import com.github.ludmylla.foodapi.domain.model.User;
import com.github.ludmylla.foodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @PostMapping
    public ResponseEntity<UserModel> create (@RequestBody @Valid UserWithPasswordInputModel userInput){
        User user = userInputDisassembler.toDomainModel(userInput);
        User userCreate = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModelAssembler.toModel(userCreate));
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll(){
        List<User> list = userService.findAll();
        return ResponseEntity.ok(userModelAssembler.toCollectionModel(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(userModelAssembler.toModel(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody @Valid UserInputModel userInput){
        User user = userInputDisassembler.toDomainModel(userInput);
        userInputDisassembler.copyToDomainObject(userInput, user);
        User userUpdate = userService.update(id, user);
        return ResponseEntity.ok(userModelAssembler.toModel(userUpdate));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordInputModel password){
        userService.changePassword(id, password.getPasswordActual(), password.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
