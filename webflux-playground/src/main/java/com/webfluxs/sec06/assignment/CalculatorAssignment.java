package com.webfluxs.sec06.assignment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import java.util.function.BiFunction;

@Configuration
public class CalculatorAssignment {

    @Bean
    public RouterFunction<ServerResponse> calculator(){
        return RouterFunctions.route()
                .path("calculator", this::calculatorRoutes)
                .build();
    }

    private RouterFunction<ServerResponse> calculatorRoutes() {
        return RouterFunctions.route()
                .GET("/{a}/0", request -> ServerResponse.ok().bodyValue("Please provide a value for b other than 0"))
                .GET("/{a}/{b}", isOperation("+"), handle((a, b) -> a + b))
                .GET("/{a}/{b}", isOperation("-"), handle((a, b) -> a - b))
                .GET("/{a}/{b}", isOperation("*"), handle((a, b) -> a * b))
                .GET("/{a}/{b}", isOperation("/"), handle((a, b) -> a / b))
                .GET("/{a}/{b}", request -> ServerResponse.badRequest().bodyValue("Invalid Operation"))
                .build();
    }

    private RequestPredicate isOperation(String s) {
       return RequestPredicates.headers(h->s.equals(h.firstHeader("operation")));
    }

    private HandlerFunction<ServerResponse> handle(BiFunction<Integer, Integer, Integer> operation) {
        return request -> {
            var a = Integer.parseInt(request.pathVariable("a"));
            var b = Integer.parseInt(request.pathVariable("b"));
            Integer result = operation.apply(a, b);
            return ServerResponse.ok().bodyValue(result);
        };
    }
}
