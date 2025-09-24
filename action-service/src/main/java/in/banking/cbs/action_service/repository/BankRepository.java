package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer> {


    Optional<Bank> findByBankName(String bankName);
}
