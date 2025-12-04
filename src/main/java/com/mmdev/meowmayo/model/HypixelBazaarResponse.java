package com.mmdev.meowmayo.model;

import java.util.Map;

public class HypixelBazaarResponse {
    private boolean success;
    private long lastUpdated;
    private Map<String, Product> products;

    public HypixelBazaarResponse() {}

    public boolean isSuccess() {
        return success;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }
}