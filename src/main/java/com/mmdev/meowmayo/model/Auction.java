package com.mmdev.meowmayo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auction {
    @JsonProperty("uuid")
    private String auctionId;

    @JsonProperty("item_bytes")
    private String itemBytes;

    @JsonProperty("price")
    private long price;

    @JsonProperty("bin")
    private boolean bin;

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getItemBytes() {
        return itemBytes;
    }

    public void setItemBytes(String itemBytes) {
        this.itemBytes = itemBytes;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isBin() {
        return bin;
    }

    public void setBin(boolean bin) {
        this.bin = bin;
    }
}
