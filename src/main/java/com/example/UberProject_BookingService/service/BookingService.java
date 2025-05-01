package com.example.UberProject_BookingService.service;

import com.example.UberProject_BookingService.dto.CreateBookingDto;
import com.example.UberProject_BookingService.dto.CreateBookingResponseDto;
import com.example.UberProject_EntityService.models.Booking;
import org.springframework.http.ResponseEntity;

public interface BookingService {

    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) ;
}
