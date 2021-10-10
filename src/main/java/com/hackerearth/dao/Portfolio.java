package com.hackerearth.dao;

import java.util.Map;

public class Portfolio {
    private String tokenId;
    private Map<String, Order> orders;
    private Map<String, Integer> stockHoldings;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    public Map<String, Integer> getStockHoldings() {
        return stockHoldings;
    }

    public void setStockHoldings(Map<String, Integer> stockHoldings) {
        this.stockHoldings = stockHoldings;
    }
}
