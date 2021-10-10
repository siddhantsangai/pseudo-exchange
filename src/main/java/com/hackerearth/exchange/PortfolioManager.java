package com.hackerearth.exchange;

import com.hackerearth.dao.Order;
import com.hackerearth.dao.OrderManagementFlag;
import com.hackerearth.dao.Portfolio;

import java.util.Map;

public class PortfolioManager {
    public static void managePortfolioHoldings(Portfolio portfolio, String symbol, String side, int qtyDelta) {
        Map<String, Integer> portfolioStockHoldings = portfolio.getStockHoldings();
        int currentQty = 0;
        if (portfolioStockHoldings.containsKey(symbol))
            currentQty = portfolioStockHoldings.get(symbol);
        if (side.equals("B"))
            portfolioStockHoldings.put(symbol, (currentQty + qtyDelta));
        else
            portfolioStockHoldings.put(symbol, (currentQty - qtyDelta));
    }

    public static void managePortfolioOrders(Order order, Portfolio portfolio, OrderManagementFlag flag) {
        Map<String, Order> orders = portfolio.getOrders();
        if (flag == OrderManagementFlag.ADD)
            orders.put(order.getOrderId(), order);
        else
            orders.remove(order.getOrderId());
    }
}
