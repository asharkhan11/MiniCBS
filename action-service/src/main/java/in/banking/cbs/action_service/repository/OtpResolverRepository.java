package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.OtpResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpResolverRepository extends JpaRepository<OtpResolver, Integer> {

    Optional<OtpResolver> findByOtp(int otp);

}
