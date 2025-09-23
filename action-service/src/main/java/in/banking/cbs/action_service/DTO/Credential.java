package in.banking.cbs.action_service.DTO;

import in.banking.cbs.action_service.utility.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credential {

    private int credentialId;
    private String username;
    private String password;
    private Set<Roles> roles = new HashSet<>();

}
