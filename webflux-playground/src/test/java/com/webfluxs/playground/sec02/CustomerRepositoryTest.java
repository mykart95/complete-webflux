package com.webfluxs.playground.sec02;

import com.webfluxs.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;


public class CustomerRepositoryTest extends AbstractTest{
    private static final Logger log= LoggerFactory.getLogger(CustomerRepositoryTest.class);

    private final CustomerRepository repository;
    @Autowired
    public CustomerRepositoryTest(CustomerRepository repository) {
        this.repository = repository;
    }

    @Test
    public void findAll(){
        this.repository.findAll()
                .doOnNext(c->log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById(){
        this.repository.findById(2)
                .doOnNext(c->log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}
