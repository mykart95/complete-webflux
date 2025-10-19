package com.webfluxs.sec06.mapper;

import com.webfluxs.sec06.dto.CustomerDto;
import com.webfluxs.sec06.entity.Customer;

public class EntityDtoMapper {

    public static Customer toEntity(CustomerDto dto){
        Customer customer=new Customer();
        customer.setName(dto.name());
        customer.setId(dto.id());
        customer.setEmail(dto.email());
        return customer;
    }

    public static CustomerDto toDto(Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}
