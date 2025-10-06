package in.banking.cbs.action_service.DTO;

import jakarta.validation.constraints.Min;
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
public class LoanRequestDTO {

    @NotNull(message = "Loan amount is required")
    @Min(value = 1000, message = "Minimum loan amount is 1000")
    private double amount;

    @NotNull(message = "Loan term (in months) is required")
    @Min(value = 6, message = "Minimum loan term is 6 months")
    private int termInMonths;

    private String purpose;

}
