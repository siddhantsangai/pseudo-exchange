package com.hackerearth.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stock {
    @JsonProperty("Stock Symbol")
    String symbol;

    @JsonProperty("Security Name")
    String fullName;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
