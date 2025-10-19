package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PostTest extends AbstractWebClient{
    private final WebClient client = createWebClient();

    @Test
    public void postBodyValue(){
        Product product = new Product(null, "iphone", 1000);
        this.client.post()
                .uri("/lec03/product")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void postBody(){
       var product= Mono.fromSupplier(()->new Product(null, "samsung", 900))
                .delayElement(java.time.Duration.ofSeconds(1));
        this.client.post()
                .uri("/lec03/product")
                .body(product, Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
