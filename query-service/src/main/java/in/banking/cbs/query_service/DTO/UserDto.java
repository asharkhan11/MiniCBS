package in.banking.cbs.query_service.DTO;

import in.banking.cbs.query_service.utility.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "bank name must be provided")
    private String bankName;

    @NotBlank(message = "branch name must be provided")
    private String branchName;

    @NotBlank(message = "first name must not be blank")
    private String firstName;

    @NotBlank(message = "last name must not be blank")
    private String lastName;

    @NotNull(message = "date of birth must be provided in format yyyy-mm-dd")
    private LocalDate dob;

    @Email(message = "email must be valid")
    @NotNull(message = "email must not be null")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotNull(message = "role must be provided as : [ADMIN, MANAGER, EMPLOYEE, CUSTOMER]")
    private UserRole role;

    @NotBlank(message = "phone number must be provided")
    @Length(min = 10, max = 10, message = "phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "address must not be blank")
    private String address;

}
