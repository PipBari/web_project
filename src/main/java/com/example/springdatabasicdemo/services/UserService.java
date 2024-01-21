package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService<ID>{
    List<UserDto> getAll();

    Optional<UserDto> findUser(UUID id);

    Optional<UserDto> findByUsername(String username);

    UserDto add(UserDto userDto);

    void delete(UUID id);

    UserDto deactivateAccount(UUID id);

    void updateUserRole(UUID userId, UUID roleId);

    UserDto update(UUID id, UserDto userDto);
}
