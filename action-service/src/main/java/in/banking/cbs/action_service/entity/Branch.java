package in.banking.cbs.action_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "branches",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_id", "ifsc_code"}),
        indexes = @Index(name = "index_branch_name", columnList = "name", unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_seq")
    @SequenceGenerator(name = "branch_seq", sequenceName = "branch_seq", allocationSize = 1)
    private int branchId;

    @Column(nullable = false, unique = true)
    private String branchName;

    private String address;
    private String city;
    private String state;

    @Column(nullable = false, length = 16)
    private String ifscCode;

    private String contactNumber;
    private int managerId; // optional, not enforced as FK

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;


}