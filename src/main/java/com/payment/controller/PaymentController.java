package com.payment.controller;

import com.payment.model.PaymentRequest;
import com.payment.model.PaymentResponse;
import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Main page
     */
    @GetMapping("/")
    public String showPaymentForm(Model model) {
        return "index";
    }

    /**
     * Redirects to payment form
     * @return
     */
    @PostMapping("/payments")
    public Object processPayment(
            @ModelAttribute PaymentRequest request, Model model
    ) {

        try{
        PaymentResponse response = paymentService.createPayment(request);
            return new RedirectView(response.getResult()
                    .getRedirectUrl());
        }catch (ResponseStatusException e){
            model.addAttribute("error", "Ошибка оплаты: " + e.getMessage());
            return "error";
        }


    }
}
