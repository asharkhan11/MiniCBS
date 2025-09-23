package in.banking.cbs.action_service.utility;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBankBranchId implements Serializable {
    private int userId;
    private int bankId;
    private int branchId;
}
