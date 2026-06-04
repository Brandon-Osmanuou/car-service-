package com.carrental.car.service;

import com.carrental.car.model.Car;
import com.carrental.car.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Car> getAvailableCars() {
        return carRepository.findByAvailable(true);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar) {
        Car existing = getCarById(id);
        existing.setMake(updatedCar.getMake());
        existing.setModel(updatedCar.getModel());
        existing.setYear(updatedCar.getYear());
        existing.setLicensePlate(updatedCar.getLicensePlate());
        return carRepository.save(existing);
    }

    public void deleteCar(Long id) {
        carRepository.delete(getCarById(id));
    }

    /**
     * Called by lease-service via HTTP when a lease is created or completed.
     *
     * In the monolith this was a direct Java call inside CarLeaseService.
     * Now it's an HTTP endpoint that lease-service calls remotely via OpenFeign.
     * This is the key architectural difference between Phase 1 and Phase 2.
     */
    public Car updateAvailability(Long id, boolean available) {
        Car car = getCarById(id);
        car.setAvailable(available);
        return carRepository.save(car);
    }
}
