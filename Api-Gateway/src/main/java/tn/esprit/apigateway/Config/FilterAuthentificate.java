package tn.esprit.apigateway.Config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Component
public class FilterAuthentificate extends AbstractGatewayFilterFactory<FilterAuthentificate.Config> {
    @Autowired
    private WebClient.Builder webClient;

    public FilterAuthentificate( ) {
        super(Config.class);
    }
    @NoArgsConstructor
    public static class Config {
        // Put the configuration properties
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
            System.out.println("||||||||||||||||  "+ request.getPath() +"   |||||||||||||||||");



            if (!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }


            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            System.out.println(token);


            return webClient.build()
                    .get()
                    .uri("http://user-service/biochar/user-service"+request.getPath())
                    .header("Authorization",   token)
                    .retrieve().bodyToMono(String.class)
                    .map(userDto -> {
                        exchange.getRequest()
                                .mutate()
                                .header("X-auth-user-id", String.valueOf(userDto));
                        return exchange;
                    }).flatMap(chain::filter).onErrorResume(error -> {
                        System.out.println("Error Happened");
                        HttpStatus errorCode = null;
                        String errorMsg = "";
                        if (error instanceof WebClientResponseException) {
                            WebClientResponseException webCLientException = (WebClientResponseException) error;
                            errorCode = webCLientException.getStatusCode();
                            errorMsg = webCLientException.getStatusText();
                            System.out.println( "1111" + errorCode +"  "+errorMsg );
                        } else {
                            errorCode = HttpStatus.BAD_GATEWAY;
                            errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            System.out.println( "22222" +  errorCode +"  "+errorMsg );
                        }
                        return  onError( exchange,  errorMsg,  errorCode);
                    });
            //  return chain.filter(exchange);
        };
    }
    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus errorCode)  {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorCode);
        byte[] bytes = errorMsg.getBytes(StandardCharsets.UTF_8);
        response.writeWith(Mono.just(bytes).map(t -> dataBufferFactory.wrap(t)));
        return response.setComplete();
    }

}
