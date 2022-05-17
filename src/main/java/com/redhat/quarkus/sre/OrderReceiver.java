package com.redhat.quarkus.sre;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.quarkus.sre.domain.Order;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;

@ApplicationScoped
public class OrderReceiver {

    @Inject
    MeterRegistry registry;

    @Inject
    Logger logger;
    
    @Incoming("orders-in")
    public CompletionStage<Void> consume(Message<Order> orderMessage) {
        Order order = orderMessage.getPayload();
        Timer timer = registry.timer("sre.inventory.tempo.consumo");
        

        Duration between = Duration.between(order.getCreationDateTime(), LocalDateTime.now());
        timer.record(between);
        logger.infof("Duration in millis: %s", between.toMillis());

        return orderMessage.ack();
    }

}
