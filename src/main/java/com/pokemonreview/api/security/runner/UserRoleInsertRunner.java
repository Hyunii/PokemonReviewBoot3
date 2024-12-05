package com.pokemonreview.api.security.runner;

import com.pokemonreview.api.security.models.RoleEntity;
import com.pokemonreview.api.security.models.UserEntity;
import com.pokemonreview.api.security.repository.RoleRepository;
import com.pokemonreview.api.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("local")
@Order(2)
public class UserRoleInsertRunner implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("#### AdminRole User Insert 시작");
        UserEntity adminUser = new UserEntity();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("123456"));
        adminUser.setFirstName("dooly");
        adminUser.setLastName("park");

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ROLE_ADMIN");
        adminUser.setRoles(Collections.singletonList(adminRole));
        userRepository.save(adminUser);

        System.out.println("#### UserRole User Insert 시작");
        UserEntity user = new UserEntity();
        user.setUsername("boot");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setFirstName("spring");
        user.setLastName("kim");

        RoleEntity role = new RoleEntity();
        role.setName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

//        RoleEntity roles = roleRepository.findByName("ROLE_USER")
//                .orElseGet(() -> {
//                    RoleEntity role = new RoleEntity();
//                    role.setName("ROLE_USER");
//                    return roleRepository.save(role);
//                });

    }
}