package com.webfluxs.sec04.contoller;

import com.webfluxs.sec04.dto.CustomerDto;
import com.webfluxs.sec04.exceptions.ApplicationExceptions;
import com.webfluxs.sec04.service.CustomerService;
import com.webfluxs.sec04.validator.RequestValidator;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> allCustomers(){
        return this.customerService.getAllCustomer();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> allCustomers(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size) {
        return this.customerService.getAllCustomers(page, size)
                .collectList();
    }
    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable Integer id){
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> mono){
//        Mono<CustomerDto> transform = mono.transform(customerDtoMono -> (Publisher<CustomerDto>) RequestValidator.validate());
//        return this.customerService.save(transform);
        return mono.transform(customerDtoMono ->(Publisher<CustomerDto>)RequestValidator.validate())
                .as(this.customerService::save);
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> mono){
        return mono.transform(customerDtoMono ->(Publisher<CustomerDto>) RequestValidator.validate())
                .as(validReq-> this.customerService.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));

    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id){
        return this.customerService.deleteCustomer(id)
                .filter(b->b)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then();
    }
}
