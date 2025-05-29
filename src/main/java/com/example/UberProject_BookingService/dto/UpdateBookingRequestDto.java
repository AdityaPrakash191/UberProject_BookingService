package com.example.UberProject_BookingService.dto;

import com.example.UberProject_EntityService.models.BookingStatus;
import lombok.*;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDto {

    private BookingStatus status;
    private Optional<Long> driverId;
}
