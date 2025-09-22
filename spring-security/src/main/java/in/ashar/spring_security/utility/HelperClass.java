package in.ashar.spring_security.utility;

import in.ashar.spring_security.entity.Roles;
import in.ashar.spring_security.exception.NotFoundException;
import in.ashar.spring_security.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HelperClass {

    @Autowired
    private RolesRepository rolesRepository;

    public Set<Roles> getRolesFromName(List<String> roleNames) {
        // Fetch matching roles from DB
        Set<Roles> foundRoles = rolesRepository.findByRoleIn(roleNames);

        // Extract role names from found roles
        Set<String> foundRoleNames = foundRoles.stream()
                .map(Roles::getRole)
                .collect(Collectors.toSet());

        // Find missing roles
        List<String> missingRoles = roleNames.stream()
                .filter(role -> !foundRoleNames.contains(role))
                .toList();

        // If any missing → throw error
        if (!missingRoles.isEmpty()) {
            throw new NotFoundException(
                    "One or more roles not found. Missing: " + missingRoles +
                            ". Valid roles in DB: " + foundRoleNames
            );
        }

        return foundRoles;
    }



}
