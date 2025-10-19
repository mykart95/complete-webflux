package com.webfluxs.sec06.config;

import com.webfluxs.sec06.dto.CustomerDto;
import com.webfluxs.sec06.exceptions.ApplicationExceptions;
import com.webfluxs.sec06.service.CustomerService;
import com.webfluxs.sec06.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustomerRequestHandler {

    @Autowired
    private CustomerService customerService;

    public Mono<ServerResponse> allCustomers(ServerRequest request) {
        return ServerResponse.ok().body(this.customerService.getAllCustomers(), CustomerDto.class);
    }

    public Mono<ServerResponse> paginatedCustomers(ServerRequest request) {
        var page = request.queryParam("page").map(Integer::parseInt).orElse(1);
        var size = request.queryParam("size").map(Integer::parseInt).orElse(3);
        return this.customerService.getAllCustomers(page, size)
                .collectList()
                .flatMap(list -> ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .flatMap(customer -> ServerResponse.ok().bodyValue(customer));
    }

   public Mono<ServerResponse> saveCustomer(ServerRequest request) {
       return request.bodyToMono(CustomerDto.class)
               .transform(RequestValidator.validate())
               .flatMap(this.customerService::saveCustomer)
               .flatMap(customer -> ServerResponse.ok().bodyValue(customer));
   }

   public Mono<ServerResponse> updateCustomer(ServerRequest request) {
       var id = Integer.parseInt(request.pathVariable("id"));
       return request.bodyToMono(CustomerDto.class)
               .transform(RequestValidator.validate())
               .flatMap(dto -> this.customerService.updateCustomer(id, Mono.just(dto)))
               .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
               .flatMap(customer -> ServerResponse.ok().bodyValue(customer));
   }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then(ServerResponse.ok().build());
    }
}