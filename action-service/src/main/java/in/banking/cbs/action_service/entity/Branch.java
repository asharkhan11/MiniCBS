package in.banking.cbs.action_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "branches",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_id", "ifsc_code"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int branchId;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    @JsonBackReference("bank-branches")
    private Bank bank;

    @Column(nullable = false)
    private String name;

    private String address;
    private String city;
    private String state;

    @Column(nullable = false, length = 16)
    private String ifscCode;

    private String contactNumber;
    private int managerId; // optional, not enforced as FK

    @Column(nullable = false, updatable = false,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(nullable = false,
            columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "branch")
    @Builder.Default
    private Set<UserBankBranch> userBankBranches = new HashSet<>();
}