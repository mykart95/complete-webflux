package com.webfluxs.sec08.controller;

import com.webfluxs.sec08.dto.ProductDto;
import com.webfluxs.sec08.dto.UploadResponse;
import com.webfluxs.sec08.service.ProductService;
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

    @PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> flux){
        log.info("invoked");
        return this.productService.save(flux)
                .then(this.productService.getProductCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }

    @GetMapping(value = "download", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDto> getAllProducts(){
        return this.productService.getAllProducts();
    }

}
