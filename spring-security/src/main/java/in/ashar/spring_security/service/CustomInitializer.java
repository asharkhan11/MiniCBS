package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Roles;
import in.ashar.spring_security.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void run(String... args) throws Exception {

        List<String> roleNames = List.of("CUSTOMER","EMPLOYEE","MANAGER","ADMIN");

        List<Roles> roles = new ArrayList<>();

        for (String roleName : roleNames) {
            Roles role = Roles.builder()
                    .role(roleName.toUpperCase())
                    .build();
            roles.add(role);
        }

        rolesRepository.saveAll(roles);

    }
}
