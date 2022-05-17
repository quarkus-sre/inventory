package com.redhat.quarkus.sre.inventory.domain;

import java.math.BigDecimal;

public class Product { 

    private String sku;

    private String name;

    private BigDecimal value;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}