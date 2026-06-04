package com.carrental.car.repository;

import com.carrental.car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByAvailable(boolean available);

    List<Car> findByMakeContainingIgnoreCase(String make);
}
