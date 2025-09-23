package in.banking.cbs.action_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDto {

    private String name;
    private String headOfficeAddress;
    private String contactNumber;
    private String email;

}
