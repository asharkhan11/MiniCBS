package in.banking.cbs.action_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpResolver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp_seq")
    @SequenceGenerator(name = "otp_seq", sequenceName = "otp_seq", allocationSize = 20)
    private int otpId;

    private int otp;
    private int credentialId;

    @Column(updatable = false)
    private LocalDateTime expiry;

}
