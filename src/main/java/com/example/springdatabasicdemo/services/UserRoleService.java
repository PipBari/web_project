package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.dtos.UserRoleDto;
import com.example.springdatabasicdemo.models.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleService<ID>{
    UserRoleDto add(UserRoleDto userRoleDto);

    List<UserRoleDto> getAll();

    Optional<UserRoleDto> findUserRole(UUID id);

    Optional<UserRoleDto> findUserRoleName(Role role);

    UserRoleDto delete(UserRoleDto userRoleDto);
}
