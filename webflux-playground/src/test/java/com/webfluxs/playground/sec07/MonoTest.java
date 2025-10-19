package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

public class MonoTest extends AbstractWebClient {

    private WebClient client= createWebClient();

    @Test
    public void simpleGet() throws InterruptedException {
        this.client
                .get()
                .uri("/lec01/product/1")
                .retrieve()
                .bodyToMono(Product.class)
                .subscribe(print());

        Thread.sleep(2000);
    }

    @Test
    public void concurrentRequest() throws InterruptedException {
        for (int i = 0; i <= 100; i++) {
            this.client
                    .get()
                    .uri("/lec01/product/{id}", i)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .subscribe(print());
        }


        Thread.sleep(2000);
    }
}
