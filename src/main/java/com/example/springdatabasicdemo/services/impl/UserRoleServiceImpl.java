package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.dtos.UserRoleDto;
import com.example.springdatabasicdemo.models.UserRole;
import com.example.springdatabasicdemo.repositories.UserRoleRepository;
import com.example.springdatabasicdemo.services.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService<Integer> {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper){
        this.userRoleRepository=userRoleRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public UserRoleDto add(UserRoleDto userRoleDto) {
        UserRole userRole = modelMapper.map(userRoleDto, UserRole.class);
        return modelMapper.map(userRoleRepository.save(userRole), UserRoleDto.class);
    }

    @Override
    public List<UserRoleDto> getAll() {
        return userRoleRepository.findAll().stream().map((c) -> modelMapper.map(c, UserRoleDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<UserRoleDto> findUserRole(UUID id) {
        return userRoleRepository.findById(id)
                .map(userRole -> modelMapper.map(userRole, UserRoleDto.class));
    }

    @Override
    public UserRoleDto delete(UserRoleDto userRoleDto) {
        userRoleRepository.deleteById(userRoleDto.getId());
        return userRoleDto;
    }
}
