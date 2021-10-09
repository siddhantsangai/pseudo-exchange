package com.hackerearth.controller;

import com.hackerearth.dao.Order;
import com.hackerearth.dao.Portfolio;
import com.hackerearth.dao.Token;
import com.hackerearth.datastore.PortfolioDataStore;
import com.hackerearth.exchange.MatchingEngine;
import com.hackerearth.exchange.TokenGenerator;
import com.hackerearth.factory.PortfolioFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RouteController {

    private static final Logger LOGGER = LogManager.getLogger(RouteController.class);

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private MatchingEngine matchingEngine;

    @Autowired
    private PortfolioDataStore portfolioDataStore;

    @GetMapping("/register")
    public ResponseEntity<Token> generateToken() {
        Token token = tokenGenerator.getToken();
        Portfolio portfolio = PortfolioFactory.getNewPortfolio(token.getTokenId());
        portfolioDataStore.pushPortfolioData(token.getTokenId(), portfolio);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/sell")
    public ResponseEntity<String> sellOrder(@Valid @RequestBody Order order
    ) {
        order.setTimeStamp(System.currentTimeMillis());
        matchingEngine.pushToSellOrderBook(order);
        LOGGER.info(order.toString());
        return ResponseEntity.ok("Sell Order placed.");
    }

    @PutMapping("/buy")
    public ResponseEntity<String> buyOrder(@Valid @RequestBody Order order
    ) {
        order.setTimeStamp(System.currentTimeMillis());
        matchingEngine.pushToBuyOrderBook(order);
        LOGGER.info(order.toString());
        return ResponseEntity.ok("Buy Order placed.");
    }

    @GetMapping("/portfolio")
    public ResponseEntity<Portfolio> getPortfolio(@Valid @RequestBody Token tokenId){
        LOGGER.info(tokenId.getTokenId());
        return ResponseEntity.ok(portfolioDataStore.getPortfolioData().get(tokenId.getTokenId()));
    }

}
