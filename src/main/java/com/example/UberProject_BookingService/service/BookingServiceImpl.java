package com.example.UberProject_BookingService.service;

import com.example.UberProject_BookingService.apis.LocationServiceApi;
import com.example.UberProject_BookingService.dto.*;
import com.example.UberProject_BookingService.repositories.BookingRepository;
import com.example.UberProject_BookingService.repositories.DriverRepository;
import com.example.UberProject_BookingService.repositories.PassengerRepository;
import com.example.UberProject_EntityService.models.Booking;
import com.example.UberProject_EntityService.models.BookingStatus;
import com.example.UberProject_EntityService.models.Driver;
import com.example.UberProject_EntityService.models.Passenger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;

//    private final static String LOCATION_SERVICE = "http://localhost:7777";


    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi , DriverRepository driverRepository){
        this.passengerRepository = passengerRepository;
        this.bookingRepository =  bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
        this.driverRepository = driverRepository;
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

        processNearByDriverAsync(dto);
//
//       ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", dto ,DriverLocationDto[].class );
//
//       if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
//           List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
//           driverLocations.forEach(driverLocationDto ->
//                   System.out.println("Id : " +driverLocationDto.getDriverId()+" "+"lat : "+driverLocationDto.getLatitude()+" lon: "+driverLocationDto.getLongitude())
//           );
//
//       }


         return CreateBookingResponseDto.builder()
            .bookingId(newBooking.getId())
            .bookingStatus(newBooking.getBookingStatus().toString())
//            .driver(Optional.of(newBooking.getDriver()))
            .build();



    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto requestDto, Long bookingId) {
        Optional<Driver> driver = driverRepository.findById(requestDto.getDriverId().get());

        bookingRepository.updateBookingStatusAndDriverBookingById(bookingId, BookingStatus.SCHEDULED, driver.get() );
         Optional<Booking> booking = bookingRepository.findById(bookingId);
         return  UpdateBookingResponseDto
                 .builder()
                 .bookingId(bookingId)
                 .bookingStatus(booking.get().getBookingStatus().toString())
//                 .driver(Optional.of(booking.get().getDriver()))
                 .driver(BookingServiceImpl.convertToDriverDto(driver))
                 .build();


    }

    private void processNearByDriverAsync(GetNearByDriverRequestDto requestDto){

        Call<DriverLocationDto[]> call = locationServiceApi.getNearByDrivers(requestDto);

        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {

                if(response.isSuccessful()&& response.body() != null){
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocationDto ->
                   System.out.println("Id : " +driverLocationDto.getDriverId()+" "+"lat : "+driverLocationDto.getLatitude()+" lon: "+driverLocationDto.getLongitude())
                 );

                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static DriverDto convertToDriverDto(Optional<Driver> driver){
        return DriverDto
                .builder()
                .driverId(driver.get().getId())
                .createdAt(driver.get().getCreatedAt())
                .updatedAt(driver.get().getUpdatedAt())
                .name(driver.get().getName())
                .aadharNumber(driver.get().getAadharCard())
                .licenseNumber(driver.get().getLicenseNumber())
                .phoneNumber(driver.get().getPhoneNumber())
                .rating(driver.get().getRating())
                .build();
    }
}
