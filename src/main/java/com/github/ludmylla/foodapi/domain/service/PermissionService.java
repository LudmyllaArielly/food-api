package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Permission;
import com.github.ludmylla.foodapi.domain.repository.PermissionRepository;
import com.github.ludmylla.foodapi.domain.service.exceptions.PermissionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission findById(Long permissionId){
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
