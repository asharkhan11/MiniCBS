package in.ashar.spring_security.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {


    public static String getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
