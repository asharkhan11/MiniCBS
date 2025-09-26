package in.banking.cbs.query_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Table(indexes = @Index(name = "index_bank_name", columnList = "name", unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_seq")
    @SequenceGenerator(name = "bank_seq", sequenceName = "bank_seq", allocationSize = 1)
    private int bankId;

    @Column(nullable = false, unique = true)
    private String bankName;

    private String headOfficeAddress;
    private String contactNumber;
    private String email;

    @OneToOne
    @JoinColumn(name = "adminId", nullable = false, unique = true)
    private Admin admin;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}