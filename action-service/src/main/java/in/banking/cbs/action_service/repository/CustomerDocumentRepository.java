package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Integer> {
    List<CustomerDocument> findAllByCustomerId(int customerId);
}
