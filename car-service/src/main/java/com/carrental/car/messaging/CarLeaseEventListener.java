package com.carrental.car.messaging;

import com.carrental.car.service.CarService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @RabbitListener listens on the queue continuously.
 * Every time lease-service publishes a message, this method fires.
 *
 * Key point: car-service has NO idea who sent this message.
 * It just receives an event and reacts to it.
 * That's true decoupling — compare to Phase 2 where lease-service
 * called car-service directly and waited for a response.
 *
 * If car-service is down when lease-service publishes:
 * → message sits in RabbitMQ queue
 * → when car-service comes back up, it processes the message
 * → nothing is lost
 *
 * That was impossible in Phase 2.
 */
@Component
public class CarLeaseEventListener {

    private final CarService carService;

    public CarLeaseEventListener(CarService carService) {
        this.carService = carService;
    }

    @RabbitListener(queues = RabbitMQConfig.CAR_LEASE_QUEUE)
    public void handleCarLeasedEvent(CarLeasedEvent event) {
        System.out.println("Received event: " + event);

        switch (event.action()) {
            case "LEASED"   -> carService.updateAvailability(event.carId(), false);
            case "RETURNED" -> carService.updateAvailability(event.carId(), true);
            default -> System.out.println("Unknown action: " + event.action());
        }
    }
}