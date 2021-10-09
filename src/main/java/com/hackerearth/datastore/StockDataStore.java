package com.hackerearth.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerearth.dao.Stock;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class StockDataStore {

    private Map<String, String> listOfStock;

    StockDataStore() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/stocks2b1d6fe.json");
        Stock[] stocks = objectMapper.readValue(file, Stock[].class);
        listOfStock = new HashMap<>();
        Arrays.stream(stocks).forEach(stock -> {
            listOfStock.put(stock.getSymbol(),stock.getFullName());
        });
    }

    public Map<String, String> getListOfStock() {
        return listOfStock;
    }
}
