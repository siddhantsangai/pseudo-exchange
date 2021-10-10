package com.hackerearth.controller;

import com.hackerearth.dao.Order;
import com.hackerearth.dao.Portfolio;
import com.hackerearth.dao.Token;
import com.hackerearth.datastore.PortfolioDataStore;
import com.hackerearth.datastore.StockDataStore;
import com.hackerearth.exchange.MatchingEngine;
import com.hackerearth.factory.OrderIdGenerator;
import com.hackerearth.factory.PortfolioFactory;
import com.hackerearth.factory.TokenGenerator;
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
    private OrderIdGenerator orderIdGenerator;

    @Autowired
    private MatchingEngine matchingEngine;

    @Autowired
    private PortfolioDataStore portfolioDataStore;

    @Autowired
    private StockDataStore stockDataStore;

    @GetMapping("/register")
    public ResponseEntity generateToken() {
        Token token = tokenGenerator.getToken();
        Portfolio portfolio = PortfolioFactory.getNewPortfolio(token.getTokenId());
        portfolioDataStore.pushPortfolioData(token.getTokenId(), portfolio);
        return HttpResponse.getTokenSuccessResponse(token);
    }

    @PutMapping("/sell")
    public ResponseEntity sellOrder(@Valid @RequestBody Order order
    ) {
        if (!stockDataStore.getListOfStock().containsKey(order.getSymbol())) {
            return HttpResponse.getFailureResponse("Invalid Symbol");
        } else if (!portfolioDataStore.getPortfolioData().containsKey(order.getTokenId()))
            return HttpResponse.getFailureResponse("Invalid Token id");
        else if (order.getQuantity() <= 0) {
            return HttpResponse.getFailureResponse("Invalid Order Quantity");
        } else if (!order.getSide().equals("S") && !order.getSide().equals("B"))
            return HttpResponse.getFailureResponse("Order side can be B or S");
        order.setTimeStamp(System.currentTimeMillis());
        order.setOrderId(orderIdGenerator.getOrderId());
        matchingEngine.pushToSellOrderBook(order);
        LOGGER.info(order.toString());
        return HttpResponse.getOrderSuccessResponse(order.getOrderId());
    }

    @PutMapping("/buy")
    public ResponseEntity buyOrder(@Valid @RequestBody Order order
    ) {
        if (!stockDataStore.getListOfStock().containsKey(order.getSymbol())) {
            return HttpResponse.getFailureResponse("Invalid Symbol");
        } else if (!portfolioDataStore.getPortfolioData().containsKey(order.getTokenId()))
            return HttpResponse.getFailureResponse("Invalid Token id");
        else if (order.getQuantity() <= 0) {
            return HttpResponse.getFailureResponse("Invalid Order Quantity");
        } else if (!order.getSide().equals("S") && !order.getSide().equals("B"))
            return HttpResponse.getFailureResponse("Order side can be B or S");
        order.setTimeStamp(System.currentTimeMillis());
        order.setOrderId(orderIdGenerator.getOrderId());
        matchingEngine.pushToBuyOrderBook(order);
        LOGGER.info(order.toString());
        return HttpResponse.getOrderSuccessResponse(order.getOrderId());
    }

    @GetMapping("/portfolio")
    public ResponseEntity getPortfolio(@Valid @RequestBody Token tokenId) {
        if (!portfolioDataStore.getPortfolioData().containsKey(tokenId.getTokenId()))
            return HttpResponse.getFailureResponse("Invalid Token id");
        LOGGER.info(tokenId.getTokenId());
        Portfolio portfolio = portfolioDataStore.getPortfolioData().get(tokenId.getTokenId());
        return HttpResponse.getPortfolioSuccessResonse(portfolio);
    }

}
