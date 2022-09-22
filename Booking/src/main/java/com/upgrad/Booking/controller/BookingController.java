package com.upgrad.Booking.controller;

import com.upgrad.Booking.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.Booking.dto.BookingDto;
import com.upgrad.Booking.dto.PaymentDto;
import com.upgrad.Booking.entities.BookingInfoEntity;
import com.upgrad.Booking.exception.handler.BookingIdNotFoundException;
import com.upgrad.Booking.exception.handler.InvalidPaymentMethodException;


@RestController
@RequestMapping("/hotel/")
public class BookingController {

    BookingService bookingService;
    ModelMapper modelMapper;

    // autowired booking service and model mapper
    @Autowired
    public BookingController(BookingService bookingService,ModelMapper modelMapper){
        this.bookingService=bookingService;
        this.modelMapper=modelMapper;
    }

    // end point 1
    @PostMapping("/booking")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingInfoEntityDto){

        BookingInfoEntity bookingInfoEntity = modelMapper.map(bookingInfoEntityDto,BookingInfoEntity.class);
        BookingInfoEntity savedBooking=bookingService.createBooking(bookingInfoEntity);
        BookingDto savedBookingDto=modelMapper.map(savedBooking, BookingDto.class);

        return new ResponseEntity(savedBookingDto, HttpStatus.CREATED);
    }


    // end point 2
    @PostMapping("/booking/{id}/transaction")
    public ResponseEntity<BookingDto> createPayment(@PathVariable("id") int id, @RequestBody PaymentDto transactionDetailsEntityDto) throws InvalidPaymentMethodException, BookingIdNotFoundException {

        BookingInfoEntity bookingInfoEntity= bookingService.createPayment(transactionDetailsEntityDto,id);
        BookingDto bookingDto=modelMapper.map(bookingInfoEntity, BookingDto.class);
        return new ResponseEntity(bookingDto,HttpStatus.CREATED);
    }

}
