package in.ashar.spring_security.controller;

import in.ashar.spring_security.dto.CredentialDto;
import in.ashar.spring_security.entity.Credential;
import in.ashar.spring_security.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PutMapping
    public ResponseEntity<CredentialDto> updateCredential(@RequestBody  CredentialDto credentialDto){
        return ResponseEntity.ok(credentialService.updateCredential(credentialDto));
    }

    @PutMapping("/{credentialId}")
    public ResponseEntity<Credential> updateCredentialById(@PathVariable int credentialId, @RequestBody Credential credential){
        return ResponseEntity.ok(credentialService.updateCredentialById(credentialId, credential));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCredential(){
        credentialService.deleteCredential();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/ids")
    public ResponseEntity<Void> deleteAllCredentialsByIds(@RequestBody List<Integer> credentialIds){
        credentialService.deleteAllCredentialsByIds(credentialIds);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Credential> getCredential(){
        return ResponseEntity.ok(credentialService.getCredentialByUsername());
    }


    @GetMapping("/{credentialId}")
    public ResponseEntity<Credential> getCredentialById(@PathVariable int credentialId){
        return ResponseEntity.ok(credentialService.getCredentialById(credentialId));
    }

}
