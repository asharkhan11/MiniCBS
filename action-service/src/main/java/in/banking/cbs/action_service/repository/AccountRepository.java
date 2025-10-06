package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.Account;
import in.banking.cbs.action_service.utility.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


    Optional<Account> findByAccountNumber(String accountNumber);
<<<<<<< Updated upstream

    Optional<Account> findByCustomerId(int customerId);

    boolean existsByAccountNumber(String toAccountNumber);
<<<<<<< HEAD
=======
>>>>>>> Stashed changes
=======

    Optional<Account> findByCustomerIdAndAccountType(int customerId, AccountType accountType);
>>>>>>> 8b7b261b9879a62986cef2e5a579c5fabb9ef621
}
