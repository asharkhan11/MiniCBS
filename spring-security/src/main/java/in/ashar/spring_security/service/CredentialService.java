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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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


    public Credential createCredential(CredentialDto credentialDto) {

        if(credentialRepository.existsByUsername(credentialDto.getUsername())){
            throw new AlreadyExistsException("username already exists");
        }

        Credential credential = new Credential();
        credential.setUsername(credentialDto.getUsername());
        credential.setPassword(passwordEncoder.encode(credentialDto.getPassword()));

        List<String> roleNames = credentialDto.getRoleNames();

        Set<Roles> roles = helperClass.getRolesFromName(roleNames);

        credential.setRoles(roles);

        return credentialRepository.save(credential);
    }


    public Credential createCredentialWithEntity(@Valid Credential cred) {

//        if(credentialRepository.existsByUsername(cred.getUsername())){
//            throw new AlreadyExistsException("username already exists");
//        }

        List<String> roleNames = cred.getRoles().stream().map(Roles::getRole).toList();
        Set<Roles> roles = helperClass.getRolesFromName(roleNames);

        Credential credential = new Credential();
        credential.setUsername(cred.getUsername());
        credential.setPassword(passwordEncoder.encode(cred.getPassword()));

        credential.setRoles(roles);

        return credentialRepository.save(credential);

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


    public Credential updateCredentialById(int credentialId, Credential credential) {

        Credential existingCredential = credentialRepository.findById(credentialId).orElseThrow(() -> new NotFoundException("credentials not found"));
//
//        if(credentialRepository.existsByUsername(credential.getUsername())){
//            throw new AlreadyExistsException("Username already exists");
//        }

        existingCredential.setUsername(credential.getUsername());

        existingCredential.setPassword(passwordEncoder.encode(credential.getPassword()));

        existingCredential.setRoles(credential.getRoles());

        return credentialRepository.save(existingCredential);

    }


    public void deleteCredential() {
        String username = SecurityHelper.getCurrentUser();
        credentialRepository.deleteByUsername(username);
    }


    public void deleteAllCredentialsByIds(List<Integer> credentialIds) {

        credentialRepository.deleteAllById(credentialIds);

    }


    public Credential getCredentialByUsername() {


        String username = SecurityHelper.getCurrentUser();

        Credential credential = credentialRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Credentials not found"));

//
//        CredentialDto credentialDto = new CredentialDto();
//        credentialDto.setUsername(username);
//        credentialDto.setPassword(credential.getPassword());
//        credentialDto.setRoleNames(credential.getRoles().stream().map(Roles::getRole).toList());

        return credential;

    }

    public Credential getCredentialById(int credentialId) {


        Credential credential = credentialRepository.findById(credentialId).orElseThrow(()-> new NotFoundException("Credentials not found with id : "+ credentialId));


//        CredentialDto credentialDto = new CredentialDto();
//        credentialDto.setUsername(credential.getUsername());
//        credentialDto.setPassword(credential.getPassword());
//        credentialDto.setRoleNames(credential.getRoles().stream().map(Roles::getRole).toList());

        return credential;

    }

    public Credential getCredentialByEmail(String email) {


        Optional<Credential> credential = credentialRepository.findByUsername(email);

//        CredentialDto credentialDto = new CredentialDto();
//        credentialDto.setUsername(credential.getUsername());
//        credentialDto.setPassword(credential.getPassword());
//        credentialDto.setRoleNames(credential.getRoles().stream().map(Roles::getRole).toList());

        return credential.orElse(null);

    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {

        return passwordEncoder.matches(rawPassword, encodedPassword);

    }
}
