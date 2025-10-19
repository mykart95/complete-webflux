package com.webfluxs.sec08.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<com.webfluxs.sec08.entity.Product, Integer> {
}
