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
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq")
    private int customerId;

    @Column(nullable = false, length = 100)
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String phone;
    private String address;

    @Column(nullable = false, unique = true)
    private int credentialId;

    @ManyToMany
    @Builder.Default
    private Set<Branch> branches = new HashSet<>();


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime joinedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

}
