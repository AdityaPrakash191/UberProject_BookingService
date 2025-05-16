package com.example.UberProject_BookingService.repositories;

import com.example.UberProject_EntityService.models.Booking;
import com.example.UberProject_EntityService.models.BookingStatus;
import com.example.UberProject_EntityService.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus =:status, b.driver =:driver WHERE b.id =:id")
    void updateBookingStatusAndDriverBookingById(@Param("id") long id , @Param("status") BookingStatus status , @Param("driver") Driver driver);
}
