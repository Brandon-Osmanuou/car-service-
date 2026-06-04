package com.carrental.car.messaging;

/**
 * The event message that lease-service publishes
 * and car-service consumes.
 *
 * Keep events simple — just the data needed.
 * action = "LEASED" means mark car unavailable
 * action = "RETURNED" means mark car available again
 */
public record CarLeasedEvent(
        Long carId,
        Long leaseId,
        String action  // "LEASED" or "RETURNED"
) {}