package com.webfluxs.sec09.service;

import com.webfluxs.sec09.dto.ProductDto;
import com.webfluxs.sec09.mapper.EntityDtoMapper;
import com.webfluxs.sec09.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Mono<ProductDto> save(Mono<ProductDto> mono){
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(this.productRepository::save)
                .map(EntityDtoMapper::toDto)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Flux<ProductDto> productStream(){
        return this.sink.asFlux();
    }
}
