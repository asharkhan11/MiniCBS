package in.ashar.spring_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(columnList = "username", unique = true))
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int credentialId;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> roles = new HashSet<>();

}
