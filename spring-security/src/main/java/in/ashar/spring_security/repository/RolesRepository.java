package in.ashar.spring_security.repository;

import in.ashar.spring_security.entity.Roles;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByRole(String name);

    boolean existsByRole(String name);

    void deleteByRole(String roleName);

    Set<Roles> findByRoleIn(List<String> roleNames);
}
