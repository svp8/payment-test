package com.payment.service;


import com.payment.model.ErrorResponse;
import com.payment.model.PaymentRequest;
import com.payment.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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
                                        clientResponse.statusCode(),
                                        "API error: " + errorBody.getMessage())))
                )
                .bodyToMono(PaymentResponse.class)
                .block();
    }
}