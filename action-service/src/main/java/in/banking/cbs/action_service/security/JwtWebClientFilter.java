package in.banking.cbs.action_service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import reactor.core.publisher.Mono;

public class JwtWebClientFilter {

    public static ExchangeFilterFunction addJwtToken() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            String jwt = getJwtFromSecurityContext();

            if (jwt != null) {
                ClientRequest authorizedRequest = ClientRequest.from(request)
                        .header("Authorization", "Bearer " + jwt)
                        .build();
                return Mono.just(authorizedRequest);
            }

            return Mono.just(request);
        });
    }

    private static String getJwtFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() != null) {
            return authentication.getCredentials().toString();
        }

        return null;
    }
}
