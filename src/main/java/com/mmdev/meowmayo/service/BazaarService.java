package com.mmdev.meowmayo.service;

import com.mmdev.meowmayo.model.*;
import com.mmdev.meowmayo.utils.NbtDecoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BazaarService {
    private final WebClient hypixelBazaarWebClient;
    private final WebClient hypixelAuctionWebClient;
    private final AtomicLong lastSuccessfulBazaarUpdate = new AtomicLong(0);

    private volatile Map<String, Product> currentBazaarData;
    private volatile Map<String, LowestPriceCache> auctionData = new ConcurrentHashMap<>();


    public BazaarService(
            @Qualifier("hypixelBazaarWebClient") WebClient webClient,
            @Qualifier("hypixelAuctionWebClient") WebClient auctionWebClient
    ) {
        this.hypixelBazaarWebClient = webClient;
        this.hypixelAuctionWebClient = auctionWebClient;
    }

    public AtomicLong getLastSuccessfulBazaarUpdate() {
        return lastSuccessfulBazaarUpdate;
    }

    @Scheduled(fixedDelay = 300000)
    public void scheduledBazaarRefresh() {
        fetchAndStoreBazaarData();
    }

    @Scheduled(fixedDelay = 60000)
    public void scheduledAuctionSearch() {
        fetchAndStoreAuctionData();
    }

    private void fetchAndStoreBazaarData() {
        System.out.println("SCHEDULER: Initiating full Bazaar data refresh...");

        try {
            Mono<HypixelBazaarResponse> responseMono = hypixelBazaarWebClient.get()
                    .retrieve()
                    .bodyToMono(HypixelBazaarResponse.class);

            HypixelBazaarResponse response = responseMono.block();

            if (response != null && response.isSuccess() && response.getProducts() != null) {
                currentBazaarData = response.getProducts();
                lastSuccessfulBazaarUpdate.set(System.currentTimeMillis());
                System.out.println("SCHEDULER: Bazaar data successfully updated at " + lastSuccessfulBazaarUpdate.get());
            } else {
                System.err.println("SCHEDULER ERROR: Hypixel API call failed or returned success=false.");
            }
        } catch (Exception e) {
            System.err.println("SCHEDULER FATAL ERROR: Could not connect to Hypixel API. Data remains stale. Reason: " + e.getMessage());
        }
    }

    private void fetchAndStoreAuctionData() {
        System.out.println("SCHEDULER: Initiating Auction data search...");

        try {
            Mono<HypixelAuctionResponse> responseMono = hypixelAuctionWebClient.get()
                    .retrieve()
                    .bodyToMono(HypixelAuctionResponse.class);

            HypixelAuctionResponse response = responseMono.block();

            if (response != null && response.isSuccess()) {
                processEndedAuctions(response.getAuctions());
                System.out.println("SCHEDULER: Auction data successfully updated at " + System.currentTimeMillis());
            } else {
                System.err.println("SCHEDULER ERROR: Hypixel API call failed or returned success=false.");
            }
        } catch (Exception e) {
            System.err.println("SCHEDULER FATAL ERROR: Could not connect to Hypixel API. Data remains stale. Reason: " + e.getMessage());
        }
    }

    private void processEndedAuctions(List<Auction> endedAuctions) {
        for (Auction auction : endedAuctions) {
            if (auction.isBin() || auction.getPrice() > 0) {
                String itemId = NbtDecoder.decodeItemId(auction.getItemBytes());

                if (itemId.equals("PET")) continue;

                System.out.println("AUCTIONS: Found Item: " + itemId);

                long newPrice = auction.getPrice();

                auctionData.putIfAbsent(itemId, new LowestPriceCache());
                LowestPriceCache lowestPriceCache = auctionData.get(itemId);

                long oldLowestPrice = lowestPriceCache.getLowestPrice();

                if (oldLowestPrice  > 0) {
                    long minAllowedPrice = (long) (lowestPriceCache.getLowestPrice() * 0.15);

                    if (newPrice < minAllowedPrice) {
                        System.out.println("LOW_SKIP:  Outlier detected for " + itemId + " of price " + newPrice);
                    }
                }

                lowestPriceCache.addAndClean(newPrice, System.currentTimeMillis());
            }
        }
    }

    public ItemPrice getPrice(String itemId) {
        if ((currentBazaarData == null || currentBazaarData.isEmpty()) && auctionData.isEmpty()) {
            throw new RuntimeException("Price data is not available.");
        }

        if (currentBazaarData.containsKey(itemId)) {
            Product product = currentBazaarData.get(itemId.toUpperCase());

            if (product != null && product.getQuickStatus() != null) {
                QuickStatus status = product.getQuickStatus();

                return new ItemPrice(
                        itemId.toUpperCase(),
                        status.getBuyPrice(),
                        status.getSellPrice()
                );
            }
        }

        if (auctionData.containsKey(itemId)) {
            LowestPriceCache lowestPriceCache = auctionData.get(itemId.toUpperCase());

            if (lowestPriceCache.getLowestPrice() > 0) {
                return new ItemPrice(
                        itemId.toUpperCase(),
                        lowestPriceCache.getLowestPrice(),
                        lowestPriceCache.getHighestPrice()
                );
            }
        }

        throw new IllegalArgumentException("Item ID '" + itemId + "' not found in memory data.");
    }
}
