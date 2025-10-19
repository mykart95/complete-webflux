package com.webfluxs.sec03.service;

import com.webfluxs.sec03.dto.CustomerDto;
import com.webfluxs.sec03.mapper.EntityDtoMapper;
import com.webfluxs.sec03.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Flux<CustomerDto> getAllCustomer(){
        return this.customerRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> getCustomerById(Integer id){
        return this.customerRepository.findById(id)
                .map(EntityDtoMapper::toDto);
    }
    public Flux<CustomerDto> getAllCustomers(Integer page, Integer size) {
        return this.customerRepository.findBy(PageRequest.of(page - 1, size)) // zero-indexed
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> save(Mono<CustomerDto> mono){
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(entity->this.customerRepository.save(entity))
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> mono){
       return this.customerRepository.findById(id)
                .flatMap(entity->mono)
                .map(EntityDtoMapper::toEntity)
                .doOnNext(c->c.setId(id))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Boolean> deleteCustomer(Integer id){
        return this.customerRepository.deleteCustomerById(id);
    }

}
