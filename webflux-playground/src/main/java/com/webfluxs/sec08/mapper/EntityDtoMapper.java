package com.webfluxs.sec08.mapper;

import com.webfluxs.sec08.dto.ProductDto;
import com.webfluxs.sec08.entity.Product;

public class EntityDtoMapper {
    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        return product;
    }

    public static ProductDto toDto(Product entity) {
        return new ProductDto(
                entity.getId(),
                entity.getDescription(),
                entity.getPrice()
        );
    }
}
