package com.webfluxs.sec05.mapper;

import com.webfluxs.sec05.dto.CustomerDto;
import com.webfluxs.sec05.entity.Customer;

public class EntityDtoMapper {

    public static Customer toEntity(CustomerDto dto){
        Customer customer=new Customer();
        customer.setName(dto.getName());
        customer.setId(dto.getId());
        customer.setEmail(dto.getEmail());
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
