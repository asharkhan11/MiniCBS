package in.banking.cbs.action_service.DTO;

import in.banking.cbs.action_service.utility.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoUpdateCredentials {

    @Email(message = "email must be valid")
    @NotNull(message = "email must not be null")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotNull(message = "role must be provided as : [ADMIN, MANAGER, EMPLOYEE, CUSTOMER]")
    private UserRole role;


}
