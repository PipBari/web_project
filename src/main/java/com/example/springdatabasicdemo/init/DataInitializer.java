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
        // Бренды
        BrandDto b1 = new BrandDto(null, "УАЗ");
        BrandDto bc1 = brandService.add(b1);
        BrandDto b2 = new BrandDto(null, "УРАЛ");
        BrandDto bc2 = brandService.add(b2);
        BrandDto b3 = new BrandDto(null, "КАМАZ");
        BrandDto bc3 = brandService.add(b3);
        // Модели
        ModelDto m1 = new ModelDto(null, "УАЗ-469", Category.Car, "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/ParkPatriot2015part4-12.jpg/1200px-ParkPatriot2015part4-12.jpg", 1969, 2001, bc1);
        ModelDto mc1 = modelService.add(m1);
        ModelDto m2 = new ModelDto(null, "Урал-4320", Category.Truck, "https://upload.wikimedia.org/wikipedia/commons/b/bc/Ural-4320.jpg", 1976, 2011, bc2);
        ModelDto mc2 = modelService.add(m2);
        ModelDto m3 = new ModelDto(null, "КамАЗ-63968 Тайфун-К", Category.Truck, "https://upload.wikimedia.org/wikipedia/commons/9/9d/April_9th_rehearsal_in_Alabino_of_2014_Victory_Day_Parade_%28558-17%29.jpg", 2011, 2023, bc3);
        ModelDto mc3 = modelService.add(m3);
        // Роли
        UserRoleDto ur1 = new UserRoleDto(null, Role.USER);
        UserRoleDto urc1 = userRoleService.add(ur1);
        UserRoleDto ur2 = new UserRoleDto(null, Role.ADMIN);
        UserRoleDto urc2 = userRoleService.add(ur2);
        // Аккаунты
        UserDto us1 = new UserDto(null, "Frede", passwordEncoder.encode("123"), "Mishka", "Frede", true, "https://yt3.googleusercontent.com/NAadweMiZy6uxCXZPsYuRZtDAw3RP49MSO3ZD2D9vx93OMByPb4VQC_R7QH91PrVuPYzXiEru6A=s900-c-k-c0x00ffffff-no-rj", urc2);
        UserDto uss1 = userService.add(us1);
        UserDto us2 = new UserDto(null, "DedDetrov", passwordEncoder.encode("123"), "Глад", "Валакас", true, "https://m.media-amazon.com/images/I/31+nhvDVDbL._SX354_SY354_BL0_QL100__UXNaN_FMjpg_QL85_.jpg", urc1);
        UserDto uss2 = userService.add(us2);
        // Объявления
        OfferDto o1 = new OfferDto(null, "", Engine.DIESEL, "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/ParkPatriot2015part4-12.jpg/1200px-ParkPatriot2015part4-12.jpg", 20, 100000, Transmission.MANUAL, mc1, uss1);
        OfferDto os1 = offerService.add(o1);
        OfferDto o2 = new OfferDto(null, "Урал реальный", Engine.DIESEL, "https://upload.wikimedia.org/wikipedia/commons/b/bc/Ural-4320.jpg", 10, 100000, Transmission.MANUAL, mc2, uss2);
        OfferDto os2 = offerService.add(o2);
    }
}
