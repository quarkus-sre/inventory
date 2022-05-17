package com.redhat.quarkus.sre.inventory;


import com.redhat.quarkus.sre.inventory.domain.Order;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<Order> {
    public OrderDeserializer() {
        super(Order.class);
    }
}