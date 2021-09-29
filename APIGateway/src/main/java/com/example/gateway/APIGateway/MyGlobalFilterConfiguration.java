package com.example.gateway.APIGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class MyGlobalFilterConfiguration {
     final Logger logger = LoggerFactory.getLogger(MyGlobalFilterConfiguration.class);

     @Order(1)
     @Bean
     public GlobalFilter secondFilter(){
         return (exchange, chain) -> {
             logger.info("My second Pre Filter Executed");
             return chain.filter(exchange).then(Mono.fromRunnable(()->{
                 logger.info("My last post filter execurted");
             }));
         };
     }

    @Order(2)
    @Bean
    public GlobalFilter thirdFilter(){
        return (exchange, chain) -> {
            logger.info("My third Pre Filter Executed");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info("My second post filter execurted");
            }));
        };
    }

    @Order(3)
    @Bean
    public GlobalFilter fourthFilter(){
        return (exchange, chain) -> {
            logger.info("My fourth Pre Filter Executed");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info("My first post filter execurted");
            }));
        };
    }

}
