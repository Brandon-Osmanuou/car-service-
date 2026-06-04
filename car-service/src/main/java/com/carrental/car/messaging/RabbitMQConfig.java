package com.carrental.car.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declares the queue that both services share.
 *
 * Queue name must match exactly in both services.
 * durable = true means the queue survives RabbitMQ restarts.
 *
 * Jackson2JsonMessageConverter tells Spring to serialize/deserialize
 * messages as JSON automatically — no manual parsing needed.
 */
@Configuration
public class RabbitMQConfig {

    public static final String CAR_LEASE_QUEUE = "car-lease-queue";

    @Bean
    public Queue carLeaseQueue() {
        return new Queue(CAR_LEASE_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}