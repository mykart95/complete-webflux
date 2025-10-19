package com.webfluxs.sec04.validator;

import com.webfluxs.sec03.dto.CustomerDto;
import com.webfluxs.sec04.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

//    Function
//            <? super Mono<CustomerDto>,
//                    ? extends org.reactivestreams.Publisher<V>>
    public static UnaryOperator <Mono<CustomerDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationExceptions.missingValidEmail());
    }

    public static Predicate<CustomerDto> hasName(){
        return dto -> Objects.nonNull(dto.name());
    }

    public static Predicate<CustomerDto> hasValidEmail(){
        return dto -> Objects.nonNull(dto.email()) && dto.email().contains("@");
    }
}
