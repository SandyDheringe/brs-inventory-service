package com.brsinventory.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusInventoryService {

    BusInventoryRepository busInventoryRepository;
    @Autowired
    BusInventoryService(BusInventoryRepository busInventoryRepository){
        this.busInventoryRepository = busInventoryRepository;
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

}
