package com.payment.service;


import com.payment.model.ErrorResponse;
import com.payment.model.PaymentRequest;
import com.payment.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final WebClient webClient;

    @Autowired
    public PaymentService(WebClient webClient) {
        this.webClient = webClient;
    }


    public PaymentResponse createPayment(PaymentRequest request) {
        return webClient.post()
                .uri("/payments")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                                .flatMap(errorBody -> Mono.error(new ResponseStatusException(
                                        clientResponse.statusCode(),formatException(errorBody)
                                        )))
                )
                .bodyToMono(PaymentResponse.class)
                .block();
    }

    /**
     * Formats error showing specific field errors
     * @param response
     * @return
     */
    private static String formatException(ErrorResponse response) {
        if(CollectionUtils.isEmpty(response.getErrors())){
            return "API error: " + response.getMessage();
        }
        return "API error: " + response.getMessage() + ". Details: " + response.getErrors()
                .stream()
                .map(x -> x.getField() + " " + x.getDefaultMessage())
                .collect(Collectors.joining(","));
    }
}