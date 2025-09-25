package in.banking.cbs.query_service.repository;

import in.banking.cbs.query_service.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer> {


    Optional<Bank> findByBankNameIgnoreCase(String name);

    Optional<Bank> findByEmail(String email);

    List<Bank> findByHeadOfficeAddressIgnoreCase(String headOfficeAddress);

}
