package com.elktibi.TFEecommerce2022.DTO;

import com.elktibi.TFEecommerce2022.models.product.Stock;

import java.util.List;

//This class serves as a wrapper for the stock form. Check if there's another way of doing without this class later
public class StockDTO {

    private List<Stock> stocks;

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
