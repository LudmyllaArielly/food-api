package com.github.ludmylla.foodapi.api.assembler;

import com.github.ludmylla.foodapi.domain.dtos.PermissionModel;
import com.github.ludmylla.foodapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionModelAssembler {

    @Autowired
    private ModelMapper mapper;

    public PermissionModel toModel(Permission permission){
        return mapper.map(permission, PermissionModel.class);
    }

    public List<PermissionModel> toCollectionModel(List<Permission> permissions) {
        return permissions.stream()
                .map(permission -> toModel(permission))
                .collect(Collectors.toList());
    }

}
