package com.mmdev.meowmayo.model;

public class ItemPrice {
    private String itemId;
    private double buyPrice;
    private double sellPrice;

    public ItemPrice(String itemId, double buyPrice, double sellPrice) {
        this.itemId = itemId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getBuyPrice() {
        return buyPrice;
    }
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}
