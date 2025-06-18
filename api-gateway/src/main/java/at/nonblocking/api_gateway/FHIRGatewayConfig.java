package at.nonblocking.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FHIRGatewayConfig {

    @Bean
    public RouteLocator fhirRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/fhir/metadata")
                        // TODO: gather capabilities
                        .filters(f -> f.setStatus(501)) // Not implemented
                        .uri("http://httpbin.org"))
                .route("path_route", r -> r.path("/fhir/Patient/*")
                        .filters(f -> f.rewritePath("/fhir/Patient/(?<segment>.*)", "/Patient/${segment}"))
                        .uri("http://localhost:5556"))
                .route("path_route", r -> r.path("/fhir/Immunization/*")
                        .filters(f -> f.rewritePath("/fhir/Immunization/(?<segment>.*)", "/Immunization/${segment}"))
                        .uri("http://localhost:5557"))
                .build();
    }

}
