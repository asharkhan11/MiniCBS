package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByCredentialId(int credentialId);
}
