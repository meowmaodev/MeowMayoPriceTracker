package com.mmdev.meowmayo.model;

public class QuickStatus {
    private double buyPrice;
    private double sellPrice;

    public QuickStatus() {}

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}