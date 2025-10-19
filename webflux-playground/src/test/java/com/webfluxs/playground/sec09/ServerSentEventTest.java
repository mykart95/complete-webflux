package com.webfluxs.playground.sec09;

import com.webfluxs.sec09.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
@SpringBootTest(properties =  "sec=sec09")
public class ServerSentEventTest {
    private static final Logger log= LoggerFactory.getLogger(ServerSentEventTest.class);
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void serverSentEvent(){
        this.webTestClient.get()
                .uri("http://localhost:8080/products/stream/80")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(ProductDto.class)
                .getResponseBody()
                .take(3)
                .doOnNext(dto-> log.info(" received: {}", dto))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list->{
                    Assertions.assertEquals(3, list.size());
                    Assertions.assertTrue(list.stream().allMatch(dto->dto.price()<=80));
                }).expectComplete()
                .verify();
    }
}
