package com.redhat.quarkus.sre.inventory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.quarkus.sre.inventory.domain.Order;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class OrderReceiver {

    @Inject
    Logger logger;

    private Timer timer;

    OrderReceiver(MeterRegistry registry) {
        this.timer = registry.timer("sre.inventory.tempo.consumo");
    } 
    
    @Incoming("orders-in")
    @Blocking(value="myworkerpool", ordered = false)
    public CompletionStage<Void> consume(Message<Order> orderMessage) {
        Order order = orderMessage.getPayload();
        try {
            //1s 
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Duration between = Duration.between(order.getCreationDateTime(), LocalDateTime.now());
        timer.record(between);
        logger.infof("Duration in millis: %s", between.toMillis());

        return orderMessage.ack();
    }

}
