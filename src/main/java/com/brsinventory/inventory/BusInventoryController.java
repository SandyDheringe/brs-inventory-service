package com.brsinventory.inventory;

import com.brsinventory.exception.BRSResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BusInventoryController {

    private final BusInventoryService busInventoryService;

    @Autowired
    BusInventoryController(BusInventoryService busInventoryService) {
        this.busInventoryService = busInventoryService;
    }

    @PostMapping("/buses/inventories")
    ResponseEntity<BusInventory> saveBusInventory(@RequestBody BusInventory busInventory) {
        BusInventory busInventoryDetail = busInventoryService.saveBusInventory(busInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(busInventoryDetail);
    }

    @PutMapping("/buses/inventories")
    ResponseEntity<BusInventory> updateBusInventory(@RequestBody BusInventory busInventory) {
        BusInventory busInventoryDetail = busInventoryService.saveBusInventory(busInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(busInventoryDetail);
    }

    @GetMapping("/buses/inventories")
    ResponseEntity<List<BusInventory>> getAllBusInventories() {
        List<BusInventory> busInventoryDetail = busInventoryService.getAllBusesInventory();
        return ResponseEntity.status(HttpStatus.CREATED).body(busInventoryDetail);
    }

    @GetMapping("/buses/{bus_id}/inventories")
    ResponseEntity<BusInventory> getBusInventory(@PathVariable("bus_id") Integer busId) {
        Optional<BusInventory> busDetail = busInventoryService.getBusInventory(busId);
        if(busDetail.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(busDetail.get());
        }
       else {
            throw new BRSResourceNotFoundException(String.format("Bus inventory details with id %d not found",busId));
        }
    }

}
