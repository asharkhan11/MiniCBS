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
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
    @SequenceGenerator(name = "admin_seq", sequenceName = "admin_seq")
    private int adminId;

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

}
