package in.banking.cbs.action_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanAccountDto {

    @Positive(message = "loan request id must be valid positive integer")
    @NotNull(message = "loan request id must be provided")
    private int loanRequestId;

    @NotBlank(message = "currency must be provided")
    private String currency;

    @NotBlank(message = "information for KYC must be provided")
    private String kyc;

    @Positive(message = "interest rate must be a valid number")
    @NotNull(message = "interest rate must be provided")
    private float interestRate;

}
