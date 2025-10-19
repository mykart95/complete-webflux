package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class HeaderTest extends AbstractWebClient{
    private final WebClient client= createWebClient();

    @Test
    public void headerTest(){
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "order-service")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void headerWithMap(){
        Map<String, String> map = Map.of("caller-id", "product-service",
                "auth-token", "xxxx-xxxx-xxxx");
        this.client.get()
                .uri("/lec04/product/{id}", 2)
                .headers(h->h.setAll(map))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
