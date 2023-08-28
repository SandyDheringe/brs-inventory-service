package com.brsinventory.inventory;

import com.brsinventory.exception.BRSResourceNotFoundException;
import com.brsinventory.messages.BusBookingMessage;
import com.brsinventory.messages.MessageBroker;
import com.brsinventory.messages.MessageDestinationConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BusInventoryService {

    private final BusInventoryRepository busInventoryRepository;

    private final MessageBroker messageBroker;
    private final BusRouteRepository busRouteRepository;

    private final BookingRepository bookingRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    BusInventoryService(BusInventoryRepository busInventoryRepository,
                        MessageBroker messageBroker, BusRouteRepository busRouteRepository, BookingRepository bookingRepository, ObjectMapper objectMapper) {
        this.busInventoryRepository = busInventoryRepository;
        this.messageBroker = messageBroker;
        this.busRouteRepository = busRouteRepository;
        this.bookingRepository = bookingRepository;
        this.objectMapper = objectMapper;
    }

    public BusInventory saveBusInventory(BusInventory busInventory) {
        return busInventoryRepository.saveAndFlush(busInventory);
    }

    public List<BusInventory> getAllBusesInventory() {
        return busInventoryRepository.findAll();
    }

    public BusInventory getBusInventory(Integer busId) {
        BusInventory busInventory = busInventoryRepository.findByBusId(busId).orElse(null);
        if (busInventory == null) {
            initBusInventory(busId);
        }
        return busInventoryRepository.findByBusId(busId).orElse(null);
    }

    private void initBusInventory(Integer busId) {
        BusRoute busRouteDetail = busRouteRepository.findById(busId).orElse(null);
        if (busRouteDetail != null) {
            BusInventory busInventoryNew = new BusInventory();
            busInventoryNew.setBusId(busRouteDetail.getId());
            busInventoryNew.setAvailableSeats(busRouteDetail.getTotalSeats());
            busInventoryNew.setLastUpdatedDate(LocalDateTime.now());
            busInventoryRepository.saveAndFlush(busInventoryNew);
        } else {
            throw new BRSResourceNotFoundException(String.format("Bus detail of id %d not found", busId));
        }
    }

    @JmsListener(destination = MessageDestinationConst.DEST_UPDATE_INVENTORY)
    public void receiveMessage(Map<String, Object> object) {
        final BusBookingMessage busBookingMessage = objectMapper.convertValue(object, BusBookingMessage.class);
        System.out.println("Received message: " + busBookingMessage);

        BusInventory busInventory = busInventoryRepository
                .findByBusId(busBookingMessage.getBusId()).orElse(null);

        Booking booking = bookingRepository.findById(busBookingMessage.getBookingId()).orElse(null);

        if (busInventory == null) {
            initBusInventory(busBookingMessage.getBusId());
            busInventory = busInventoryRepository
                    .findByBusId(busBookingMessage.getBusId()).orElse(null);
        }

        busInventory.setAvailableSeats(busInventory.getAvailableSeats() - booking.getNoOfSeats());
        busInventory.setLastUpdatedDate(LocalDateTime.now());
        busInventoryRepository.saveAndFlush(busInventory);
        messageBroker.sendConfirmBookingMessage(MessageDestinationConst.DEST_UPDATE_BOOKING, busBookingMessage);
    }

}
