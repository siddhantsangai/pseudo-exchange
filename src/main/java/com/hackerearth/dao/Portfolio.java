package com.hackerearth.dao;

import java.util.List;
import java.util.Map;

public class Portfolio {
    private String tokenId;
    private List<Order> orders;
    private Map<String, Integer> stockHoldings;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Map<String, Integer> getStockHoldings() {
        return stockHoldings;
    }

    public void setStockHoldings(Map<String, Integer> stockHoldings) {
        this.stockHoldings = stockHoldings;
    }
}
