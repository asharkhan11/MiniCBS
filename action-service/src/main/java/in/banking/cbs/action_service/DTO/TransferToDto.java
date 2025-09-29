package in.banking.cbs.action_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferToDto {

    @NotBlank(message = "receiver's account number must be provided")
    @Length(min = 12, max = 12)
    private String toAccountNumber;

    @Positive(message = "amount must be greater than 0")
    @NotNull(message = "amount must be provided")
    private double amount;

    private String remarks;

}
