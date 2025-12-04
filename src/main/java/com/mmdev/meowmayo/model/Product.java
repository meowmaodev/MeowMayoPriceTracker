package com.mmdev.meowmayo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("quick_status")
    private QuickStatus quickStatus;

    public Product() {}

    public QuickStatus getQuickStatus() {
        return quickStatus;
    }

    public void setQuickStatus(QuickStatus quickStatus) {
        this.quickStatus = quickStatus;
    }
}