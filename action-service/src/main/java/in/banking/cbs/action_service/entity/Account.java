package in.banking.cbs.action_service.entity;

import in.banking.cbs.action_service.utility.AccountType;
import in.banking.cbs.action_service.utility.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( indexes = @Index(name = "acc_no_idx", columnList = "accountNumber", unique = true))
public class Account {

    @Id
    @SequenceGenerator(name = "acc_id_seq",sequenceName = "acc_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_id_seq")
    @Column(nullable = false)
    private long accountId;

    @Column(nullable = false)
    private int customerId;

    @Column(nullable = false, unique = true, length = 50)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    private double balance;

    @Column(length = 10)
    private String currency;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private long version;

}
