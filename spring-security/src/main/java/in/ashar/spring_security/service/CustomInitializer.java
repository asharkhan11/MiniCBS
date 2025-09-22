package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Credential;
import in.ashar.spring_security.entity.Roles;
import in.ashar.spring_security.repository.CredentialRepository;
import in.ashar.spring_security.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        String name = "ashar";
        String password = "root";
        String roleName = "ADMIN";

        if (!credentialRepository.existsByUsername(name)) {

            Roles role = new Roles(roleName);
            Credential credential = new Credential();
            credential.setUsername(name);
            credential.setPassword(passwordEncoder.encode(password));

            Set<Roles> roles = new HashSet<>();
            roles.add(role);
            credential.setRoles(roles);

            rolesRepository.save(role);
            Credential save = credentialRepository.save(credential);
            System.out.println("admin created : " + save);
        }
        System.out.printf("Admin details : name( %s ), password( %s ), role( %S )", name, password, roleName);
    }
}
