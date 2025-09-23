package in.banking.cbs.action_service.entity;

import in.banking.cbs.action_service.utility.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, length = 100)
    private String firstName;

    private String lastName;
    private LocalDate dob;

    @Column(nullable = false, unique = true)
    private int credentialId; // email, password and roles will be stored here

    private String phone;
    private String address;

    @Column(nullable = false, updatable = false,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime joinedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBankBranch> userBankBranches = new HashSet<>();
}