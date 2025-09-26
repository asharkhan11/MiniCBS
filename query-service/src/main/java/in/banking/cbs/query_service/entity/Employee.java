package in.banking.cbs.query_service.entity;

import in.banking.cbs.query_service.utility.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @SequenceGenerator(name = "emp_seq", sequenceName = "emp_seq")
    private int employeeId;

    @Column(nullable = false, length = 100)
    private String firstName;
    private String lastName;
    private float salary;
    private LocalDate dob;
    private String phone;
    private String address;

    @Column(nullable = false, unique = true)
    private int credentialId; // decoupled from security service

    @ManyToOne
    @JoinColumn(name = "branchId", nullable = false)
    private Branch branch;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime joinedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

}
