package com.mmdev.meowmayo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    final int size = 5 * 1024 * 1024;
    private final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();

    @Bean
    public WebClient hypixelBazaarWebClient(WebClient.Builder builder) {

        return builder.baseUrl("https://api.hypixel.net/skyblock/bazaar")
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public WebClient hypixelAuctionWebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://api.hypixel.net/skyblock/auctions_ended")
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public WebClient.Builder hypixelWebClientBuilder() {
        return WebClient.builder();
    }
}