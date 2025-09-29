package in.banking.cbs.action_service.entity;

import in.banking.cbs.action_service.utility.TxnStatus;
import in.banking.cbs.action_service.utility.TxnType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions",
        indexes = {
                @Index(columnList = "fromAccountNumber"),
                @Index(columnList = "toAccountNumber")
        })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "txn_seq")
    @SequenceGenerator(name = "txn_seq", sequenceName = "txn_seq")
    @Column(name = "txn_id")
    private long txnId;

    @Column(name = "branch_id")
    private int branchId;

    @Column(name = "initiated_by")
    private int customerId;

    @Column(name = "from_account_number", nullable = false)
    private String fromAccountNumber;

    @Column(name = "to_account_number", nullable = false)
    private String toAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "txn_type", length = 20)
    private TxnType txnType;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private TxnStatus status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "reference_no", length = 50)
    private String referenceNo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime txn;

}
