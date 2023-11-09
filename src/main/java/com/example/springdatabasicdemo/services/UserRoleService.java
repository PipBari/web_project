package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.dtos.UserRoleDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleService<ID>{
    UserRoleDto add(UserRoleDto userRoleDto);

    List<UserRoleDto> getAll();

    Optional<UserRoleDto> findUserRole(UUID id);

    UserRoleDto delete(UserRoleDto userRoleDto);
}
