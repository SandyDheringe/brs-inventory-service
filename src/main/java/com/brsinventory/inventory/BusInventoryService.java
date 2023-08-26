package com.brsinventory.inventory;

import com.brsinventory.util.MessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BusInventoryService {

    private final BusInventoryRepository busInventoryRepository;

    private final MessageBroker messageBroker;
    private final BusRouteRepository busRouteRepository;

    @Autowired
    BusInventoryService(BusInventoryRepository busInventoryRepository,
                        MessageBroker messageBroker, BusRouteRepository busRouteRepository) {
        this.busInventoryRepository = busInventoryRepository;
        this.messageBroker = messageBroker;
        this.busRouteRepository = busRouteRepository;
    }

    public BusInventory saveBusInventory(BusInventory busInventory) {
        return busInventoryRepository.saveAndFlush(busInventory);
    }

    public List<BusInventory> getAllBusesInventory() {
        return busInventoryRepository.findAll();
    }

    public Optional<BusInventory> getBusInventory(Integer busId) {
        return busInventoryRepository.findByBusId(busId);
    }


    @JmsListener(destination = "brsqueue")
    public void receiveMessage(String message) {
        BusInventory busInventory = busInventoryRepository.findByBusId(1).orElse(null);

        if (busInventory == null) {
            BusRoute busRouteDetail = busRouteRepository.findById(1).orElse(null);
            if (busRouteDetail != null) {
                BusInventory busInventoryNew = new BusInventory();
                busInventoryNew.setBusId(busRouteDetail.getId());
                busInventoryNew.setAvailableSeats(busRouteDetail.getTotalSeats());
                busInventoryNew.setLastUpdatedDate(LocalDateTime.now());
                BusInventory newBusInventory = busInventoryRepository.saveAndFlush(busInventoryNew);

                newBusInventory.setAvailableSeats(newBusInventory.getAvailableSeats() - 1);
                busInventoryNew.setLastUpdatedDate(LocalDateTime.now());
                busInventoryRepository.saveAndFlush(busInventoryNew);
            }
        } else {
            busInventory.setAvailableSeats(busInventory.getAvailableSeats() - 1);
            busInventory.setLastUpdatedDate(LocalDateTime.now());
            busInventoryRepository.saveAndFlush(busInventory);
        }
        messageBroker.sendMessage("brsQueue","ticket-reserved");

    }

}
