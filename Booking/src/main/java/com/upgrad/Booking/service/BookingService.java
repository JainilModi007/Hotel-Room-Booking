package com.upgrad.Booking.service;

import com.upgrad.Booking.dto.PaymentDto;
import com.upgrad.Booking.entities.BookingInfoEntity;
import com.upgrad.Booking.exception.handler.BookingIdNotFoundException;
import com.upgrad.Booking.exception.handler.InvalidPaymentMethodException;

import java.util.List;


public interface BookingService {

    public BookingInfoEntity createBooking(BookingInfoEntity bookingInfoEntity);
    public BookingInfoEntity getBookingBasedOnId(int id);
    public List<BookingInfoEntity> getAllBookings();
    public void deleteBookingID(BookingInfoEntity bookingInfoEntity);
    public BookingInfoEntity updateBooking(BookingInfoEntity bookingInfoEntity);
    public BookingInfoEntity createPayment(PaymentDto transactionDetailsEntityDto, int id) throws InvalidPaymentMethodException, BookingIdNotFoundException;

}
