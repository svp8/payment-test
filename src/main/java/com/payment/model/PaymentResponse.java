package com.payment.model;

import lombok.Data;

@Data
public class PaymentResponse {

    private String status;
    private Result result;

    @Data
    public static class Result {
        private String redirectUrl;
    }
}
