package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.CalculatorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

public class QueryParamTest extends AbstractWebClient {
    private final WebClient client = createWebClient();

    @Test
    public void uriBuilderVariable(){
        var path="/lec06/calculator";
        var query="first={first}&second={second}&operation={operation}";
        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .query(query)
                        .build(15, 5, "/"))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void uriBuilderMap(){
        var path="/lec06/calculator";
        var query="first={first}&second={second}&operation={operation}";
       var map= Map.of("first", 15,
                "second", 5,
                "operation", "*");
        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .query(query)
                        .build(map))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
