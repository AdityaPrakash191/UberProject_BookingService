package com.example.UberProject_BookingService.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GetNearByDriverRequestDto {

    private Double latitude;
    private Double longitude;

}
