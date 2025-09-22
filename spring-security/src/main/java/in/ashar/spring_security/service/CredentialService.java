package in.ashar.spring_security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.spring_security.dto.CredentialDto;
import in.ashar.spring_security.entity.Credential;
import in.ashar.spring_security.entity.Roles;
import in.ashar.spring_security.exception.AlreadyExistsException;
import in.ashar.spring_security.exception.NotFoundException;
import in.ashar.spring_security.repository.CredentialRepository;
import in.ashar.spring_security.repository.RolesRepository;
import in.ashar.spring_security.security.SecurityHelper;
import in.ashar.spring_security.utility.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HelperClass helperClass;


    public CredentialDto createCredential(CredentialDto credentialDto) {

        if(credentialRepository.existsByUsername(credentialDto.getUsername())){
            throw new AlreadyExistsException("username already exists");
        }

        Credential credential = new Credential();
        credential.setUsername(credentialDto.getUsername());
        credential.setPassword(passwordEncoder.encode(credentialDto.getPassword()));

        List<String> roleNames = credentialDto.getRoleNames();

        Set<Roles> roles = helperClass.getRolesFromName(roleNames);

        credential.setRoles(roles);

        credentialRepository.save(credential);

        return credentialDto;
    }

    public CredentialDto updateCredential(CredentialDto credentialDto) {

        String username = SecurityHelper.getCurrentUser();

        Credential existingCredential = credentialRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("credentials not found"));

        if(credentialRepository.existsByUsername(credentialDto.getUsername())){
            throw new AlreadyExistsException("Username already exists");
        }

        existingCredential.setUsername(credentialDto.getUsername());

        existingCredential.setPassword(passwordEncoder.encode(credentialDto.getPassword()));

        Set<Roles> roles = helperClass.getRolesFromName(credentialDto.getRoleNames());

        existingCredential.setRoles(roles);

        credentialRepository.save(existingCredential);

        return credentialDto;
    }


    public void deleteCredential() {
        String username = SecurityHelper.getCurrentUser();
        credentialRepository.deleteByUsername(username);
    }


    public CredentialDto getCredentialByUsername() {


        String username = SecurityHelper.getCurrentUser();

        Credential credential = credentialRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Credentials not found"));


        CredentialDto credentialDto = new CredentialDto();
        credentialDto.setUsername(username);
        credentialDto.setPassword(credential.getPassword());
        credentialDto.setRoleNames(credential.getRoles().stream().map(Roles::getRole).toList());

        return credentialDto;

    }
}
