package com.brsinventory.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bus_inventory", schema = "bus_reservation_db")
public class BusInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private Integer id;

    @Column(name = "bus_id", nullable = false)
    private Integer busId;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonIgnore
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @PrePersist
    @PreUpdate
    private void beforeSave(){
        lastUpdatedDate = LocalDateTime.now();
    }
}