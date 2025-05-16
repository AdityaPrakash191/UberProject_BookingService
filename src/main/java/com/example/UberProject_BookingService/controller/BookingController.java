package com.example.UberProject_BookingService.controller;

import com.example.UberProject_BookingService.dto.CreateBookingDto;
import com.example.UberProject_BookingService.dto.CreateBookingResponseDto;
import com.example.UberProject_BookingService.dto.UpdateBookingResponseDto;
import com.example.UberProject_BookingService.dto.UpdateBookingRequestDto;
import com.example.UberProject_BookingService.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping()
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto createBookingDto){
        return new ResponseEntity<>(bookingService.createBooking(createBookingDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateBookingRequestDto requestDto, @PathVariable Long bookingId){
        return new ResponseEntity<>(bookingService.updateBooking(requestDto,bookingId),HttpStatus.OK);
    }

}
