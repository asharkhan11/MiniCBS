package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByCredentialId(int credentialId);
}
