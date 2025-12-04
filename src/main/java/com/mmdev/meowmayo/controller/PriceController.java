package com.mmdev.meowmayo.controller;

import com.mmdev.meowmayo.model.ItemPrice;
import com.mmdev.meowmayo.service.BazaarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PriceController {

    private final BazaarService bazaarService;

    public PriceController(BazaarService bazaarService) {
        this.bazaarService = bazaarService;
    }

    @GetMapping("/price/{itemId}")
    public ItemPrice getItemPrice(@PathVariable String itemId) {
        return bazaarService.getPrice(itemId);
    }
}