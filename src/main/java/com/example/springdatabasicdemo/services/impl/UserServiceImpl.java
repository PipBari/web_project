package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.models.Offer;
import com.example.springdatabasicdemo.models.User;
import com.example.springdatabasicdemo.models.UserRole;
import com.example.springdatabasicdemo.models.enums.Role;
import com.example.springdatabasicdemo.repositories.OfferRepository;
import com.example.springdatabasicdemo.repositories.UserRepository;
import com.example.springdatabasicdemo.repositories.UserRoleRepository;
import com.example.springdatabasicdemo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService<Integer>{
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, OfferRepository offerRepository, ModelMapper modelMappe){
        this.userRepository=userRepository;
        this.userRoleRepository=userRoleRepository;
        this.offerRepository=offerRepository;
        this.modelMapper=modelMappe;
    }
    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(c -> modelMapper.map(c, UserDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<UserDto> findUser(UUID id){
        return Optional.ofNullable(modelMapper.map(userRepository.findById(id), UserDto.class));
    }
    @Override
    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDto.class));
    }
    @Override
    public UserDto add(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setCreated(LocalDate.now());
        user.setIsActive(true);
        if (userDto.getUserRoleDto() == null || userDto.getUserRoleDto().getRole() == null) {
            UserRole userRole = userRoleRepository.findByRole(Role.USER)
                    .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
            user.setUserRole(userRole);
        } else {
        }
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto delete(UserDto userDto){
        userRepository.deleteById(userDto.getId());
        return userDto;
    }
    @Override
    public UserDto deactivateAccount(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        List<Offer> offers = offerRepository.findByUser(user);
        offerRepository.deleteAll(offers);
        user.setIsActive(false);
        user.setModified(LocalDate.now());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto update(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        if (userDto.getUserRoleDto() != null && userDto.getUserRoleDto().getId() != null) {
            UserRole userRole = userRoleRepository.findById(userDto.getUserRoleDto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("UserRole not found with ID: " + userDto.getUserRoleDto().getId()));
            user.setUserRole(userRole);
        }
        user.setModified(LocalDate.now());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

}