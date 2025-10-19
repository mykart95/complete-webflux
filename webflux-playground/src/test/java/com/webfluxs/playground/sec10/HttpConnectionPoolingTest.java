package com.webfluxs.playground.sec10;

import com.webfluxs.playground.sec10.dto.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;

public class HttpConnectionPoolingTest extends AbstractWebClient{

    private final WebClient client=createWebClient(b->{
       var poolSize=1;
       var provider= ConnectionProvider.builder("karmy")
               .lifo()
               .maxConnections(poolSize)
               .pendingAcquireMaxCount(poolSize*5)
                .build();
       var httpClient= HttpClient.create(provider)
               .compress(true)
               .keepAlive(true);
       b.clientConnector(new ReactorClientHttpConnector(httpClient));
    });

    @Test
    public void concurrentRequests(){
        var max=3010;
        Flux.range(1, max)
                .flatMap(this::getProduct, max)
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list->{
                    Assertions.assertEquals(max, list.size());
                }).expectComplete()
                .verify();
    }

    private Mono<Product> getProduct(Integer id) {
        return this.client.get()
                .uri("/product/{id}",id)
                .retrieve()
                .bodyToMono(Product.class);
    }
}
