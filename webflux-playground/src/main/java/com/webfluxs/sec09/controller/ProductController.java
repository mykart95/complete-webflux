package com.webfluxs.sec09.controller;

import com.webfluxs.sec09.dto.ProductDto;
import com.webfluxs.sec09.dto.UploadResponse;
import com.webfluxs.sec09.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger log= LoggerFactory.getLogger(ProductController.class);


    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> mono){
        return this.productService.save(mono);
    }

    @GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> streamProducts(@PathVariable Integer maxPrice){
        return this.productService.productStream()
                .filter(dto->dto.price()<=maxPrice);
    }
}
