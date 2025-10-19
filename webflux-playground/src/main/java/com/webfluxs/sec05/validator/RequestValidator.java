package com.webfluxs.sec05.validator;

import com.webfluxs.sec05.dto.CustomerDto;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

//    Function
//            <? super Mono<CustomerDto>,
//                    ? extends org.reactivestreams.Publisher<V>>
//    public static UnaryOperator <Mono<CustomerDto>> validate() {


    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return mono -> {
            if (mono == null) {
                return Mono.error(new ValidationException("Customer is null"));
            }

            return mono.flatMap(c -> {
                boolean valid = c.getName() != null &&
                        !c.getName().isBlank() &&
                        c.getEmail() != null &&
                        c.getEmail().contains("@");

                if (valid) {
                    return Mono.just(c);
                } else {
                    return Mono.error(new ValidationException("Invalid customer"));
                }
            });
        };
    }


//    public static boolean isValid(Mono<com.webfluxs.sec05.dto.CustomerDto> customer) {
//        if (customer == null) {
//            return Mono.error(new ValidationException("Customer is null"));
//        }
//
//        return customer.flatMap(c ->
//                c.getName() != null &&
//                        !c.getName().isBlank() &&
//                        c.getEmail() != null &&
//                        c.getEmail().contains("@")
//        );
//    }
//    public static Predicate<CustomerDto> hasName(){
//        return dto -> Objects.nonNull(dto.name());
//    }
//
//    public static Predicate<CustomerDto> hasValidEmail(){
//        return dto -> Objects.nonNull(dto.email()) && dto.email().contains("@");
//    }

}
