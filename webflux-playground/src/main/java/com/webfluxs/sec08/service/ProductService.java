package com.webfluxs.sec08.service;

import com.webfluxs.sec08.dto.ProductDto;
import com.webfluxs.sec08.mapper.EntityDtoMapper;
import com.webfluxs.sec08.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> save(Flux<ProductDto> fluxDto){
        return fluxDto.map(EntityDtoMapper::toEntity)
                .as(this.productRepository::saveAll)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Long> getProductCount(){
        return this.productRepository.count();
    }

    public Flux<ProductDto> getAllProducts(){
        return this.productRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }
}
