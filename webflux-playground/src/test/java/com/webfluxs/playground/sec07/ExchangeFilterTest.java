package com.webfluxs.playground.sec07;

import com.webfluxs.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

public class ExchangeFilterTest extends AbstractWebClient {
    private final WebClient client = createWebClient(b -> b.filter(tokenGenerator())
            .filter(logRequest()));

    private ExchangeFilterFunction logRequest() {
        return ((request, next) -> {
            String token = UUID.randomUUID().toString().replace("-", "");
            var isEnabled= (Boolean)request.attributes().getOrDefault("log-request",true);
            if(isEnabled) {
                log.info("requested url- {}:{}", request.method(), request.url());
            }

            return next.exchange(request);
        });
    }

    private static final Logger log= LoggerFactory.getLogger(ExchangeFilterTest.class);

    @Test
    public void exchangeFilterTest() {
        for (int i = 0; i < 5; i++) {
            this.client.get()
                    .uri("/lec09/product/{id}", i)
                    .attribute("log-request", i%2==0)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(print())
                    .then()
                    .as(StepVerifier::create)
                    .expectComplete()
                    .verify();
        }
    }
    private ExchangeFilterFunction tokenGenerator() {
        return ((request, next) -> {
            String token = UUID.randomUUID().toString().replace("-", "");
            log.info("generated token: {}", token);
            ClientRequest modifiedRequest = ClientRequest.from(request)
                    .headers(h -> h.setBearerAuth(token))
                    .build();
            return next.exchange(modifiedRequest);
        });
    }
}
