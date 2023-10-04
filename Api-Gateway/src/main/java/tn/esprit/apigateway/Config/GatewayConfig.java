package tn.esprit.apigateway.Config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,  FilterAuthentificate filterAuthentificate) {
        return builder.routes()
                .route("appointment-service", r -> r.path("/biochar/appointment-service/**").uri("lb://appointment-service"))
                .route("analysis-service", r -> r.path("/biochar/analysis/**").uri("lb://analysis-service"))
                .route("stock-service", r -> r.path("/biochar/stock-service/**")
                        //.filters(f -> f.filter(filterAuthentificate.apply( new FilterAuthentificate.Config())))
                        .uri("lb://stock-service"))
                .route("commande-service", r -> r.path("/biochar/commande/**").uri("lb://commande-service"))
                .route("hr-service", r -> r.path("/biochar/hr/**").uri("lb://hr-service"))
                .route("user-service", r -> r.path("/biochar/user-service/**").uri("lb://user-service"))
                .route("discovery-server", r -> r.path("/eureka/web").filters(f -> f.setPath("/")).uri("http://localhost:8761"))
                .route("discovery-server-static", r -> r.path("/eureka/**") .uri("http://localhost:8761"))
                .build();
    }

}


