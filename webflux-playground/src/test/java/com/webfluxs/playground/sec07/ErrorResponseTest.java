package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.CalculatorResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ErrorResponseTest extends AbstractWebClient{

    private final WebClient client = createWebClient();
    private static final Logger log = LoggerFactory.getLogger(ErrorResponseTest.class);

    @Test
    public void errorHandler(){
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10,20)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
//                .onErrorReturn(new CalculatorResponse(0, 0, "error", 0.0))
                .doOnError(WebClientResponseException.class, ex->log.info("{}", ex.getResponseBodyAs(ProblemDetail.class)))
                .onErrorReturn(WebClientResponseException.InternalServerError.class,
                        new CalculatorResponse(0, 0, "error", 0.0))
                .onErrorReturn(WebClientResponseException.BadRequest.class,
                        new CalculatorResponse(0, 0, "bad error", 0.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
     }

    @Test
    public void exchangeHandler(){
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10,20)
                .header("operation", "+")
                .exchangeToMono(this::decode)
              
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    private Mono<CalculatorResponse> decode(ClientResponse clientResponse) {
        log.info("Status code: {}", clientResponse.statusCode());
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd -> log.info("Problem detail: {}", pd))
                    .then(Mono.empty());
        }
        return clientResponse.bodyToMono(CalculatorResponse.class);
    }
}
