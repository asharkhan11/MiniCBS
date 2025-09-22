package in.ashar.spring_security.utility;

import in.ashar.spring_security.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateJwt(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();

        claims.put("type","active");
        claims.put("role", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
        );

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiry()))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        claims.put("type","inactive");
        claims.put("role", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
        );

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24*7)) //validity 7 days
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateJwt(String token, UserDetails userDetails){
        try {

            Claims claims = extractAllClaims(token);
            String issuer = claims.getIssuer();
            Date expiration = claims.getExpiration();
            String username = claims.getSubject();


            if (username.equals(userDetails.getUsername()) && issuer.equals(jwtProperties.getIssuer()) && expiration.after(new Date())) {
                return true;
            }
        }catch (Exception e){
            log.error("Error while validation jwt : {}", e.getMessage());
        }
        return false;
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractType(String token){
        return (String) extractClaim(token, claims -> claims.get("type"));
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }


}
