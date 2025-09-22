package in.ashar.spring_security.repository;

import in.ashar.spring_security.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Integer> {

    Optional<Credential> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

//    // READ
//    Optional<UserDetails> findByName(String name);
//
//    List<UserDetails> findAllByName(String name);
//
//    // UPDATE (custom implementations go in service or @Query if needed)
//
//    // DELETE
//    void deleteByName(String name);
//
//    // EXISTS
//    boolean existsByName(String name);
//
//    // COUNT
//    long countByName(String name);
}