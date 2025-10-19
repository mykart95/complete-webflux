package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class BasicAuthTest extends AbstractWebClient {
//    private final WebClient client = createWebClient(b->b.defaultHeader("caller-id","order-service"));
private final WebClient client = createWebClient(b->b.defaultHeaders(h->h.setBasicAuth("java","secret")));

    @org.junit.jupiter.api.Test
    public void basicAuthTest() {
        this.client.get()
                .uri("/lec07/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
