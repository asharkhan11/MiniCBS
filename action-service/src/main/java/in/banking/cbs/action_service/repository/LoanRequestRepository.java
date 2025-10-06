package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.LoanRequest;
import in.banking.cbs.action_service.utility.LoanRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {
    Optional<LoanRequest> findByCustomerId(int customerId);

    List<LoanRequest> findAllByBranchIdAndStatus(int branchId, LoanRequestStatus status);
}
