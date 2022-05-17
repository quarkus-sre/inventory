package com.redhat.quarkus.sre.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order { 

    private String customer;

    private String carrier;

    private List<Product> products;

    private String paymentMethod;

    private LocalDateTime creationDateTime;

    public String getCustomer() {
        return customer;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    

}