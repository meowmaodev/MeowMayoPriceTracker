package com.mmdev.meowmayo.model;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LowestPriceCache {
    private final List<PriceEntry> lowestPrices = new CopyOnWriteArrayList<>();
    private static final int MAX_PRICES = 5;

    public static class PriceEntry {
        public final long price;
        public final long timestamp;

        public PriceEntry(long price, long timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }

        public long getPrice() { return price; }
        public long getTimestamp() { return timestamp; }
    }

    public void addAndClean(long newPrice, long newTimestamp) {
        long tenMinutesAgo = Instant.now().minusSeconds(600).toEpochMilli();

        lowestPrices.removeIf(entry -> entry.getTimestamp() < tenMinutesAgo);
        lowestPrices.add(new PriceEntry(newPrice, newTimestamp));
        lowestPrices.sort(Comparator.comparing(PriceEntry::getPrice));
        if (lowestPrices.size() > MAX_PRICES) {
            List<PriceEntry> trimmed = lowestPrices.stream()
                    .limit(MAX_PRICES)
                    .toList();
            lowestPrices.clear();
            lowestPrices.addAll(trimmed);
        }
    }

    public long getLowestPrice() {
        return lowestPrices.isEmpty() ? 0 : lowestPrices.getFirst().getPrice();
    }

    public long getHighestPrice() {
        return lowestPrices.isEmpty() ? 0 : lowestPrices.getLast().getPrice();
    }

    public List<PriceEntry> getTop5Prices() {
        return lowestPrices;
    }
}