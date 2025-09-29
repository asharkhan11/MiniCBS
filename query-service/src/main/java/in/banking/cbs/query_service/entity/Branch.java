package in.banking.cbs.query_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_id", "ifsc_code"}),
        indexes = @Index(name = "index_branch_name", columnList = "name", unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    @OneToOne
    @JoinColumn(name = "managerId")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}