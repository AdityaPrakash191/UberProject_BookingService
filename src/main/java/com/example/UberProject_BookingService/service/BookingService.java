package com.example.UberProject_BookingService.service;

import com.example.UberProject_BookingService.dto.CreateBookingDto;
import com.example.UberProject_BookingService.dto.CreateBookingResponseDto;
import com.example.UberProject_BookingService.dto.UpdateBookingRequestDto;
import com.example.UberProject_BookingService.dto.UpdateBookingResponseDto;
import com.example.UberProject_EntityService.models.Booking;
import org.springframework.http.ResponseEntity;

public interface BookingService {

     CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) ;

     UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto requestDto, Long bookingId);
}
