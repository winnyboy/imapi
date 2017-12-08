package com.huione.im.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.huione.im.api.service.HystrixServiceFailure;

import feign.Feign;

@Configuration
public class FooConfiguration {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    @Bean
    public HystrixServiceFailure fb(){
        return new HystrixServiceFailure();
    }

}