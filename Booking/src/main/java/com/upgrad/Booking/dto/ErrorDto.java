package com.upgrad.Booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {

    @JsonProperty
    String message;
    @JsonProperty
    int statusCode;

    public ErrorDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}