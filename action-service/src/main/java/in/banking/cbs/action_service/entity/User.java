package in.banking.cbs.action_service.entity;

import in.banking.cbs.action_service.utility.UserStatus;
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
@Table(name = "users",
        indexes = {@Index(name = "uk_users_email", columnList = "email", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private int userId;

    @Column(nullable = false, length = 100)
    private String firstName;

    private String lastName;
    private LocalDate dob;

    @Column(nullable = false, unique = true)
    private int credentialId; // FK reference to credentials table ideally

    private String phone;
    private String address;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime joinedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBankBranch> userBankBranches = new HashSet<>();
}
