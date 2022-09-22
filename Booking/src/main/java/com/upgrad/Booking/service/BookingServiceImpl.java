package com.upgrad.Booking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import com.upgrad.Booking.dao.BookingDao;
import com.upgrad.Booking.dto.PaymentDto;
import com.upgrad.Booking.entities.BookingInfoEntity;
import com.upgrad.Booking.exception.handler.BookingIdNotFoundException;
import com.upgrad.Booking.exception.handler.InvalidPaymentMethodException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class BookingServiceImpl implements com.upgrad.Booking.service.BookingService {


    private BookingDao bookingInfoEntityDAO;
    private RestTemplate restTemplate;

    @Autowired
    public BookingServiceImpl(BookingDao bookingInfoEntityDAO, RestTemplate restTemplate){
        this.bookingInfoEntityDAO = bookingInfoEntityDAO;
        this.restTemplate=restTemplate;
    }

    @Value("${transaction.url}")
    private String transactionUrl;

    // service for end point 1

    @Override
    public BookingInfoEntity createBooking(BookingInfoEntity bookingInfoEntity) {

        bookingInfoEntity.setRoomPrice(roomPrize(bookingInfoEntity.getNumOfRooms(),bookingDays(bookingInfoEntity.getFromDate(),bookingInfoEntity.getToDate())));
        bookingInfoEntity.setRoomNumbers(roomNumbers(bookingInfoEntity.getNumOfRooms()));
        bookingInfoEntity.setBookedOn(LocalDateTime.now());

        return  bookingInfoEntityDAO.save(bookingInfoEntity);
    }

    @Override
    public BookingInfoEntity getBookingBasedOnId(int id) {
        return bookingInfoEntityDAO.getById(id);
    }

    @Override
    public List<BookingInfoEntity> getAllBookings() {
        return bookingInfoEntityDAO.findAll();
    }

    @Override
    public void deleteBookingID(BookingInfoEntity bookingInfoEntity) {
        bookingInfoEntityDAO.delete(bookingInfoEntity);
    }

    @Override
    public BookingInfoEntity updateBooking(BookingInfoEntity bookingInfoEntity) {
        return bookingInfoEntityDAO.save(bookingInfoEntity);
    }


    public int roomPrize(int numOfRooms,int numberOfDays){
        int roomPrice = 1000* numOfRooms * numberOfDays;
        return roomPrice;
    }

    public static String roomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String> numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }

        String roomNumbers=numberList.toString();
        return roomNumbers.substring(1,roomNumbers.length()-1);
    }

    private int bookingDays(LocalDate fromDate, LocalDate toDate) {
        int days = (int)ChronoUnit.DAYS.between(fromDate, toDate);
        return days;
    }


    // service for end point 2

    public BookingInfoEntity createPayment(PaymentDto transactionDetailsEntityDto, int id) throws InvalidPaymentMethodException, BookingIdNotFoundException {

        if(!(transactionDetailsEntityDto.getPaymentMode().equalsIgnoreCase("CARD")  || transactionDetailsEntityDto.getPaymentMode().equalsIgnoreCase("UPI"))){
            throw new InvalidPaymentMethodException("Invalid mode of Payment " +transactionDetailsEntityDto.getPaymentMode());
        }

        if(!(bookingInfoEntityDAO.existsByBookingId(id) )){
            throw new BookingIdNotFoundException("Invalid Booking Id " + id);
        }else if(!(bookingInfoEntityDAO.existsByBookingId(transactionDetailsEntityDto.getBookingId()))){
            throw new BookingIdNotFoundException("Invalid Booking Id " + transactionDetailsEntityDto.getBookingId());
        }

        Map<String,String> bookUriMap = new HashMap<>();

        bookUriMap.put("bookingId",String.valueOf(id));
        bookUriMap.put("paymentMode",transactionDetailsEntityDto.getPaymentMode());
        bookUriMap.put("upiId",transactionDetailsEntityDto.getUpiId());
        bookUriMap.put("cardNumber",transactionDetailsEntityDto.getCardNumber());

        int transactionId=restTemplate.postForObject(transactionUrl,bookUriMap,Integer.class);

        BookingInfoEntity bookingInfoEntity= bookingInfoEntityDAO.getById(id);
        bookingInfoEntity.setTransactionId(transactionId);

        String message = "Booking confirmed for user with aadhaar number: "
                + bookingInfoEntity.getAadharNumber()
                +    "    |    "
                + "Here are the booking details:    " + bookingInfoEntity.toString();
        System.out.println(message);

        return bookingInfoEntityDAO.save(bookingInfoEntity);

    }

}
