package com.kosh.GatewayApplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authservice-routes", p ->
                        p.path("/api/v1/auth/**").uri("lb://authservice/api/v1/auth"))
                .route("auth-docs", p ->
                        p.path("/docs/auth/**").filters(f -> f.rewritePath("^/docs/auth", "")).uri("lb://authservice"))
                .route("blog-docs", p ->
                        p.path("/docs/blog/**").filters(f -> f.rewritePath("^/docs/blog", "")).uri("lb://blogservice"))
                .route("blogservice-routes", p -> p
                        .path("/api/v1/blog/**").filters(f -> f.filter(authenticationFilter)).uri("lb://blogservice/api/v1/blog"))

                .build();
    }

}
