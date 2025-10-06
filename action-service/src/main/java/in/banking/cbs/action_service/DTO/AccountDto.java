package in.banking.cbs.action_service.DTO;

import in.banking.cbs.action_service.utility.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotNull(message = "customer id must not be null")
    @Positive(message = "customer id must be valid")
    private int customerId;

    @NotNull(message = "account type must be provided")
    private AccountType accountType;

    @PositiveOrZero(message = "initial deposit must be either zero or positive")
    @NotNull(message = "initial deposit must be provided")
    private double balance;

    @NotBlank(message = "currency must be provided")
    private String currency;

    @NotBlank(message = "information for KYC must be provided")
    private String kyc;

}
