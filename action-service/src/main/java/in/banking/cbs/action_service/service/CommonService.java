package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.ChangePasswordDto;
import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.exception.InvalidDataException;
import in.banking.cbs.action_service.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final SecurityServiceClient securityServiceClient;
    private final LoggedInUser loggedInUser;

    public void changePassword(ChangePasswordDto changePasswordDto) {

        int credentialId = loggedInUser.getCredentialId();

        Credential credential = securityServiceClient.getCredentialById(credentialId);

        String oldEncryptedPassword = credential.getPassword();

        if (!securityServiceClient.matchPassword(changePasswordDto.getOldPassword(), oldEncryptedPassword)) {
            throw new InvalidDataException("old password does not match");
        }

        credential.setPassword(changePasswordDto.getNewPassword());
        securityServiceClient.updateCredentialById(credential.getCredentialId(), credential);
    }


}
