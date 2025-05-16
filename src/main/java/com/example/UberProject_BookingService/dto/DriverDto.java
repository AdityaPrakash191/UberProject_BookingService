package com.example.UberProject_BookingService.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
    private long driverId;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String licenseNumber;
    private String aadharNumber;
    private String phoneNumber;
    private double rating;

}
