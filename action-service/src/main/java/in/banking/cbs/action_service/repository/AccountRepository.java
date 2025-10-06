package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


    Optional<Account> findByAccountNumber(String accountNumber);
<<<<<<< Updated upstream

    Optional<Account> findByCustomerId(int customerId);

    boolean existsByAccountNumber(String toAccountNumber);
=======
>>>>>>> Stashed changes
}
