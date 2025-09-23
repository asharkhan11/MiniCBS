package in.banking.cbs.action_service.entity;

import in.banking.cbs.action_service.utility.UserBankBranchId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_bank_branch",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "bank_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBankBranch {

    @EmbeddedId
    private UserBankBranchId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bankId")
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @MapsId("branchId")
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(nullable = false, updatable = false,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime assignedOn;
}
