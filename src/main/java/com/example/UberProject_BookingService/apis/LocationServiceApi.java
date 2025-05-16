package com.example.UberProject_BookingService.apis;

import com.example.UberProject_BookingService.dto.DriverLocationDto;
import com.example.UberProject_BookingService.dto.GetNearByDriverRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {

    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearByDrivers (@Body GetNearByDriverRequestDto requestDto);
}
