package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Credential;
import in.ashar.spring_security.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credential user = credentialRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRole())));
//        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRole())));

        return new User(user.getUsername(), user.getPassword(), authorities);

    }
}
