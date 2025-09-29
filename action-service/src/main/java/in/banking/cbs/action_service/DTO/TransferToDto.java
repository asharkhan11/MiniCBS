package in.banking.cbs.action_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferToDto {

    private String toAccountNumber;

    private double amount;

    private String remarks;

}
