package apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import apigateway.util.JwtHelper;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {
        // put the config prop here
    }

    @Autowired
    private JwtHelper jwtHelper;

    private String[] public_urls = { "/actuator/health", "/api/users/login", "/api/users/register", 
                                        "/api/users/resetpassword", "/api/products/search", "/api/products" };

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final String urlPath = request.getURI().getPath();

            if (isPublicUrl(urlPath)) {
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            final String token = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if (!jwtHelper.isJwtTokenValid(token)) {
                return onError(exchange, "Jwt token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean isPublicUrl(String url) {
        for (int i = 0; i < public_urls.length; i++) {
            if (url.startsWith(public_urls[i])) {
                return true;
            }
        }

        return false;
    }

}
