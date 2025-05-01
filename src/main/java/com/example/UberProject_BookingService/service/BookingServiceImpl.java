package com.example.UberProject_BookingService.service;

import com.example.UberProject_BookingService.dto.CreateBookingDto;
import com.example.UberProject_BookingService.dto.CreateBookingResponseDto;
import com.example.UberProject_BookingService.dto.DriverLocationDto;
import com.example.UberProject_BookingService.dto.GetNearByDriverRequestDto;
import com.example.UberProject_BookingService.repositories.BookingRepository;
import com.example.UberProject_BookingService.repositories.PassengerRepository;
import com.example.UberProject_EntityService.models.Booking;
import com.example.UberProject_EntityService.models.BookingStatus;
import com.example.UberProject_EntityService.models.Passenger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    private final static String LOCATION_SERVICE = "http://localhost:7777";


    public BookingServiceImpl(PassengerRepository passengerRepository,BookingRepository bookingRepository){
        this.passengerRepository = passengerRepository;
        this.bookingRepository =  bookingRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public  CreateBookingResponseDto  createBooking(CreateBookingDto bookingDetails) {

        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNED_DRIVER)

                .startLocation(bookingDetails.getStartLocation())
//                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

//   make an api call to location_service to fetch NearBy Drivers

        GetNearByDriverRequestDto dto = GetNearByDriverRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

       ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", dto ,DriverLocationDto[].class );

       if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
           List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
           driverLocations.forEach(driverLocationDto ->
                   System.out.println("Id : " +driverLocationDto.getDriverId()+" "+"lat : "+driverLocationDto.getLatitude()+" lon: "+driverLocationDto.getLongitude())
           );

       }


         return CreateBookingResponseDto.builder()
            .bookingId(newBooking.getId())
            .bookingStatus(newBooking.getBookingStatus().toString())
//            .driver(Optional.of(newBooking.getDriver()))
            .build();

    }
}
