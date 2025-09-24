package in.banking.cbs.action_service.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDto {

    private String bankName;
    private String branchName;

    private String address;
    private String city;
    private String state;

    private String ifscCode;
    private String contactNumber;
    private int managerId;

}
