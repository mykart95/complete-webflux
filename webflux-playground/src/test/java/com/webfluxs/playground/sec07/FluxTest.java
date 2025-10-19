package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class FluxTest extends AbstractWebClient{

    private  WebClient client = createWebClient();

    @Test
    public void streamingResponse(){
        this.client.get()
                .uri("/lec02/product/stream")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
