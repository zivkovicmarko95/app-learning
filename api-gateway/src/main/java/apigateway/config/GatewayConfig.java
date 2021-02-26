package apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import apigateway.filter.AuthorizationHeaderFilter;
import apigateway.filter.AuthorizationHeaderFilter.Config;

@Configuration
public class GatewayConfig {

    private final Environment env;
    private final AuthorizationHeaderFilter authHeaderFilter;

    @Autowired
    public GatewayConfig(Environment env, AuthorizationHeaderFilter authHeaderFilter) {
        this.env = env;
        this.authHeaderFilter = authHeaderFilter;
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                    .route(r -> r.path(env.getProperty("api.user.path"))
                                    .filters(f -> f.filter(authHeaderFilter.apply(new Config())))
                                    .uri(env.getProperty("api.user.uri")))
                    .route(r -> r.path(env.getProperty("api.monitoring.path"))
                                    .filters(f -> f.filter(authHeaderFilter.apply(new Config())))
                                    .uri(env.getProperty("api.monitoring.uri")))
                    .route(r -> r.path(env.getProperty("api.product.path"))
                                    .filters(f -> f.filter(authHeaderFilter.apply(new Config())))
                                    .uri(env.getProperty("api.product.uri")))
                    .route(r -> r.path(env.getProperty("api.order_backup.path"))
                                    .filters(f -> f.filter(authHeaderFilter.apply(new Config())))
                                    .uri(env.getProperty("api.order_backup.uri")))
                    .build();
    }
}
