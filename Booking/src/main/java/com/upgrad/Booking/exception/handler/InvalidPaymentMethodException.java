package com.upgrad.Booking.exception.handler;

public class InvalidPaymentMethodException extends Throwable {
    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}