package com.brsinventory.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.OK).body(busInventoryService.getBusInventory(busId));
    }

}
