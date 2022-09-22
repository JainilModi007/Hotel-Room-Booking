package com.upgrad.Booking.dao;

import com.upgrad.Booking.entities.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDao extends JpaRepository<BookingInfoEntity,Integer> {
    public Boolean existsByBookingId(int id);
}