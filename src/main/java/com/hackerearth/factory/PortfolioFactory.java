package com.hackerearth.factory;

import com.hackerearth.dao.Portfolio;

import java.util.ArrayList;
import java.util.HashMap;

public class PortfolioFactory {
    public static Portfolio getNewPortfolio(String tokenId){
        Portfolio portfolio = new Portfolio();
        portfolio.setTokenId(tokenId);
        portfolio.setStockHoldings(new HashMap<>());
        portfolio.setOrders(new ArrayList<>());
        return portfolio;
    }
}
