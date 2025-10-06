package in.banking.cbs.action_service.entity;

import in.banking.cbs.action_service.utility.LoanRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_req_seq")
    @SequenceGenerator(name = "loan_req_seq", sequenceName = "loan_req_seq")
    private int requestId;

    private double amount;

    private int termInMonths;

    private int customerId;

    private int branchId;

    private String purpose;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private LoanRequestStatus status = LoanRequestStatus.PENDING;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime requestedDate;

}
