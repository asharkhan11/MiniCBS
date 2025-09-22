package in.ashar.spring_security.controller;

import in.ashar.spring_security.dto.CredentialDto;
import in.ashar.spring_security.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PutMapping
    public ResponseEntity<CredentialDto> updateCredential(CredentialDto credentialDto){
        return ResponseEntity.ok(credentialService.updateCredential(credentialDto));
    }

    @DeleteMapping
    public ResponseEntity<CredentialDto> deleteCredential(){
        credentialService.deleteCredential();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CredentialDto> getCredential(){
        return ResponseEntity.ok(credentialService.getCredentialByUsername());
    }

}
