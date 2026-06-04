package com.carrental.car.controller;

import com.carrental.car.model.Car;
import com.carrental.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class  CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(
            @RequestParam(required = false) Boolean available) {
        List<Car> cars = (available != null)
                ? carService.getAvailableCars()
                : carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
            @PathVariable Long id,
            @Valid @RequestBody Car car) {
        return ResponseEntity.ok(carService.updateCar(id, car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /cars/{id}/availability?available=false
     *
     * This endpoint exists specifically for lease-service to call.
     * When a lease is created → lease-service calls this to mark car unavailable.
     * When a lease is completed → lease-service calls this to mark car available.
     *
     * In the monolith this was carService.updateCarAvailability() — a direct Java call.
     * Now it's an HTTP endpoint consumed by another service over the network.
     */
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Car> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {
        return ResponseEntity.ok(carService.updateAvailability(id, available));
    }
}
