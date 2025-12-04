package com.mmdev.meowmayo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HypixelAuctionResponse {
    private boolean success;

    @JsonProperty("lastUpdated")
    private long lastUpdated;

    @JsonProperty("auctions")
    private List<Auction> auctions;

    public boolean isSuccess() {
        return success;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
}
