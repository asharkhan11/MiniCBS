package in.banking.cbs.action_service.DTO;

import in.banking.cbs.action_service.utility.AccountType;
import in.banking.cbs.action_service.utility.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoUpdate {

    private AccountType accountType;

    @PositiveOrZero(message = "balance must be either zero or positive")
    @NotNull(message = "balance must be provided")
    private double balance;

    @Length(min = 2, max = 10, message = "currency must be valid")
    private String currency;

}
