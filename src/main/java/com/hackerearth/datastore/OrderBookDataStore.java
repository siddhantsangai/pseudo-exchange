package com.hackerearth.datastore;

import com.hackerearth.dao.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderBookDataStore {

    private Map<String,List<Order>> buyOrdersForAllStocks;
    private Map<String,List<Order>> sellOrdersForAllStocks;

    @Autowired
    OrderBookDataStore(StockDataStore stockDataStore){
        this.buyOrdersForAllStocks = new HashMap<>();
        this.sellOrdersForAllStocks = new HashMap<>();
        stockDataStore.getListOfStock()
                .keySet()
                .forEach(symbol -> buyOrdersForAllStocks.put(symbol, new ArrayList<>()));
        stockDataStore.getListOfStock()
                .keySet()
                .forEach(symbol -> sellOrdersForAllStocks.put(symbol, new ArrayList<>()));
    }

    public List<Order> getBuyOrderBook(String symbol) {
        return buyOrdersForAllStocks.get(symbol);
    }

    public List<Order> getSellOrderBook(String symbol) {
        return sellOrdersForAllStocks.get(symbol);
    }
}
