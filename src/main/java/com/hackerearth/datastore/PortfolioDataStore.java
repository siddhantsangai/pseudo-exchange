package com.hackerearth.datastore;

import com.hackerearth.dao.Portfolio;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PortfolioDataStore {
    Map<String, Portfolio> portfolioData;

    public PortfolioDataStore() {
        this.portfolioData = new HashMap<>();
    }

    public Map<String, Portfolio> getPortfolioData() {
        return portfolioData;
    }

    public void pushPortfolioData(String tokenId, Portfolio portfolio) {
        portfolioData.put(tokenId,portfolio);
    }
}
