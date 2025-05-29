package com.example.UberProject_BookingService.apis;

import com.example.UberProject_BookingService.dto.DriverLocationDto;
import com.example.UberProject_BookingService.dto.GetNearByDriverRequestDto;
import com.example.UberProject_BookingService.dto.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UberSocketApi {
    @POST("api/socket/newride")
    Call<Boolean> raiseRideRequest (@Body RideRequestDto requestDto);
}
