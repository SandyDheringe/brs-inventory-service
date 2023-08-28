package com.brsinventory.inventory;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking", schema = "bus_reservation_db")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer id;

    @Column(name = "bus_id", nullable = false)
    private Integer busId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "no_of_seats")
    private Integer noOfSeats;

    @Column(name = "booking_status", length = 20)
    private String bookingStatus;

    @Column(name = "total_amount")
    private Float totalAmount;

}