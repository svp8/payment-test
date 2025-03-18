package com.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private List<ErrorObj> errors;



    @Data
    public static class ErrorObj{
        private String defaultMessage;
        private String field;
    }
}
