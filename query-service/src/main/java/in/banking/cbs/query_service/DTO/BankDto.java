package in.banking.cbs.query_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDto {
    @NotBlank(message = "bank name must be provided")
    private String bankName;

    @NotBlank(message = "address must not be blank")
    private String headOfficeAddress;

    @NotBlank(message = "phone number must be provided")
    @Length(min = 10, max = 10, message = "phone number must be 10 digits")
    private String contactNumber;

    @Email(message = "email must be valid")
    @NotNull(message = "email must not be null")
    private String email;

}
