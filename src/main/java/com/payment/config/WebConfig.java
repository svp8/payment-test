package com.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://engine-sandbox.pay.tech/api/v1")
                .defaultHeader("Authorization", "Bearer cAmmvalAikARkB81fgxgMtnMbEdNbuWa")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
