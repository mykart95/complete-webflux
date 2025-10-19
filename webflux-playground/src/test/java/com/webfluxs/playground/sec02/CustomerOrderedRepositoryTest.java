package com.webfluxs.playground.sec02;

import com.webfluxs.sec02.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class CustomerOrderedRepositoryTest extends AbstractTest{
    private static final Logger log= LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void productsOrderedByCustomer(){
        this.customerOrderRepository.getProductsOrderedByCustomer("sam")
                .doOnNext(p->log.info("{}", p))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void orderDetailsByProduct(){
        this.customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(dto->log.info("{}", dto))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
