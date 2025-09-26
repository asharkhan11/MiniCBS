package in.banking.cbs.query_service.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
