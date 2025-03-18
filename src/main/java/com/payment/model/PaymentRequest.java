package com.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String paymentType = "DEPOSIT";
    private Double amount;
    private String currency;
    private Customer customer=new Customer();// without it, api throws error
}