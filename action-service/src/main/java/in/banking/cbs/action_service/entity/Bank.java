package in.banking.cbs.action_service.entity;


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
@Table(name = "banks", indexes = @Index(name = "index_bank_name", columnList = "name",unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int bankId;

    @Column(nullable = false, unique = true)
    private String name;

    private String headOfficeAddress;
    private String contactNumber;
    private String email;

    @Column(nullable = false, updatable = false,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(nullable = false,
            columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "bank")
    @JsonManagedReference("bank-branches")
    private Set<Branch> branches = new HashSet<>();

    @OneToMany(mappedBy = "bank")
    private Set<UserBankBranch> userBankBranches = new HashSet<>();
}
