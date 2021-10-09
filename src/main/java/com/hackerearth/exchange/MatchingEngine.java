package com.hackerearth.exchange;

import com.hackerearth.dao.Order;
import com.hackerearth.dao.Portfolio;
import com.hackerearth.datastore.OrderBookDataStore;
import com.hackerearth.datastore.PortfolioDataStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MatchingEngine {
    /*
    * maintain a order book - separate for sell and buy
    * sort according to price (desc for buy, asc for sell
    * if price is equal the order with higher timestamp goes down - FIFO impl
    * order matched if best buy(highest buy price) >= best sell(lowest sell price)
    * */

    private static final Logger LOGGER= LogManager.getLogger(MatchingEngine.class);
    private List<Order> buyOrders;
    private List<Order> sellOrders;
    private Comparator<Order> buyOrderComparator;
    private Comparator<Order> sellOrderComparator;

    @Autowired
    private PortfolioDataStore portfolioDataStore;

    @Autowired
    private OrderBookDataStore orderBookDataStore;

    MatchingEngine(){
        buyOrderComparator = new BuyOrderComparator();
        sellOrderComparator = new SellOrderComparator();
    }

    public void pushToBuyOrderBook(Order order){
        Portfolio portfolio = portfolioDataStore.getPortfolioData().get(order.getTokenId());
        List<Order> orders = portfolio.getOrders();
        orders.add(order);
        buyOrders=orderBookDataStore.getBuyOrderBook(order.getSymbol());
        sellOrders=orderBookDataStore.getSellOrderBook(order.getSymbol());
        buyOrders.add(order);
        buyOrders.sort(buyOrderComparator);
        match();
    }

    public void pushToSellOrderBook(Order order){
        Portfolio portfolio = portfolioDataStore.getPortfolioData().get(order.getTokenId());
        List<Order> orders = portfolio.getOrders();
        orders.add(order);
        buyOrders=orderBookDataStore.getBuyOrderBook(order.getSymbol());
        sellOrders=orderBookDataStore.getSellOrderBook(order.getSymbol());
        sellOrders.add(order);
        sellOrders.sort(sellOrderComparator);
        match();
    }

    public void match(){
        try{
            Order bestBuyOrder = buyOrders.get(0);
            Order bestSellOrder = sellOrders.get(0);
            int buyQty = bestBuyOrder.getQuantity();
            int sellQty = bestSellOrder.getQuantity();
            String buyerTokenId = bestBuyOrder.getTokenId();
            String sellerTokenId = bestSellOrder.getTokenId();
            Portfolio buyerPortfolio = portfolioDataStore.getPortfolioData().get(buyerTokenId);
            Portfolio sellerPortfolio = portfolioDataStore.getPortfolioData().get(sellerTokenId);
            if(bestBuyOrder.getPrice()>= bestSellOrder.getPrice()){
                LOGGER.info("Match found");
                //match
                if(buyQty>sellQty) {
                    PortfolioManager.managePortfolioHoldings(buyerPortfolio,bestBuyOrder.getSymbol(),bestBuyOrder.getSide(),sellQty);
                    PortfolioManager.managePortfolioHoldings(sellerPortfolio,bestSellOrder.getSymbol(), bestSellOrder.getSide(),sellQty);
                    bestBuyOrder.setQuantity(buyQty-sellQty);
                    bestSellOrder.setQuantity(0);
                    sellOrders.remove(0);
                    match();
                }
                else if(buyQty<sellQty){
                    PortfolioManager.managePortfolioHoldings(buyerPortfolio,bestBuyOrder.getSymbol(),bestBuyOrder.getSide(),buyQty);
                    PortfolioManager.managePortfolioHoldings(sellerPortfolio,bestSellOrder.getSymbol(), bestSellOrder.getSide(),buyQty);
                    bestSellOrder.setQuantity(sellQty-buyQty);
                    bestBuyOrder.setQuantity(0);
                    buyOrders.remove(0);
                    match();
                }
                else{
                    PortfolioManager.managePortfolioHoldings(buyerPortfolio,bestBuyOrder.getSymbol(),bestBuyOrder.getSide(),sellQty);
                    PortfolioManager.managePortfolioHoldings(sellerPortfolio,bestSellOrder.getSymbol(), bestSellOrder.getSide(),sellQty);
                    bestSellOrder.setQuantity(0);
                    bestBuyOrder.setQuantity(0);
                    sellOrders.remove(0);
                    buyOrders.remove(0);
                    match();
                }
            }
            else{
                LOGGER.info("No matches : ");
                LOGGER.info(" BUY Order BOOK: " + buyOrders.toString());
                LOGGER.info(" SELL Order BOOK: " + sellOrders.toString());
            }
        }
        catch (IndexOutOfBoundsException e){
            LOGGER.info("No further matches");
        }
    }
}

class BuyOrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if(o1.getPrice()>o2.getPrice())
            return -1;
        else if(o1.getPrice()<o2.getPrice())
            return 1;
        else if(o1.getPrice()==o2.getPrice()){
            if(o1.getTimeStamp() > o2.getTimeStamp())
                return 1;
            else if(o1.getTimeStamp() < o2.getTimeStamp())
                return -1;
            else
                return 0;
        }
        return 0;
    }
}

class SellOrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if(o1.getPrice()>o2.getPrice())
            return 1;
        else if(o1.getPrice()<o2.getPrice())
            return -1;
        else if(o1.getPrice()==o2.getPrice()){
            if(o1.getTimeStamp() > o2.getTimeStamp())
                return 1;
            else if(o1.getTimeStamp() < o2.getTimeStamp())
                return -1;
            else
                return 0;
        }
        return 0;
    }
}
