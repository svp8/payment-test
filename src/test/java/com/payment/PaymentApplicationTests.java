package com.payment;

import com.payment.model.PaymentRequest;
import com.payment.model.PaymentResponse;
import com.payment.service.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class PaymentApplicationTests {

	@Autowired
	PaymentService paymentService;
	@Test
	void payment() {
		PaymentRequest request = new PaymentRequest();
		request.setCurrency("USD");
		request.setAmount(1d);
		PaymentResponse payment = paymentService.createPayment(request);
		Assertions.assertNotNull(payment.getResult().getRedirectUrl());
	}

	@Test
	void fail() {
		PaymentRequest request = new PaymentRequest();
		request.setCurrency("USD3");
		request.setAmount(1d);
		ResponseStatusException responseStatusException = Assertions.assertThrows(ResponseStatusException.class, () -> paymentService.createPayment(request));
		Assertions.assertEquals("400 BAD_REQUEST \"API error: JSON parse error: Cannot construct instance of `com.neopay.model.Currency`, problem: `java.lang.IllegalArgumentException`\"",responseStatusException.getMessage());
	}



}
