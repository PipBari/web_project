package com.example.springdatabasicdemo.init;

import com.example.springdatabasicdemo.dtos.*;
import com.example.springdatabasicdemo.models.enums.Category;
import com.example.springdatabasicdemo.models.enums.Engine;
import com.example.springdatabasicdemo.models.enums.Role;
import com.example.springdatabasicdemo.models.enums.Transmission;
import com.example.springdatabasicdemo.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final BrandService brandService;
    private final ModelService modelService;
    private final OfferService offerService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final JsonDataReader jsonDataReader;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataInitializer(BrandService brandService, ModelService modelService, OfferService offerService, UserRoleService userRoleService, UserService userService, JsonDataReader jsonDataReader){
        this.brandService=brandService;
        this.modelService=modelService;
        this.offerService=offerService;
        this.userRoleService=userRoleService;
        this.userService=userService;
        this.jsonDataReader=jsonDataReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        List<BrandDto> brands = jsonDataReader.readBrandData();
        for (BrandDto brand : brands) {
            brandService.add(brand);
        }
    }

    private void seedData() {
        addRole(Role.USER);
        addRole(Role.ADMIN);
    }

    private void addRole(Role role) {
        Optional<UserRoleDto> existingRole = userRoleService.findUserRoleName(role);
        if (!existingRole.isPresent()) {
            UserRoleDto newRole = new UserRoleDto(null, role);
            userRoleService.add(newRole);
        }
    }
}
