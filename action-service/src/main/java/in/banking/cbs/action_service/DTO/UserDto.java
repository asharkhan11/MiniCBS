package in.banking.cbs.action_service.DTO;

import in.banking.cbs.action_service.utility.UserRole;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String firstName;

    private String lastName;
    private LocalDate dob;

    private String email;
    private String password;
    private UserRole role;

    private String phone;
    private String address;

}
