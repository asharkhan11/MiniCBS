package in.ashar.spring_security.utility;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@Data
public class JwtProperties {

    private String secretKey;
    private String issuer;
    private long expiry;
}
