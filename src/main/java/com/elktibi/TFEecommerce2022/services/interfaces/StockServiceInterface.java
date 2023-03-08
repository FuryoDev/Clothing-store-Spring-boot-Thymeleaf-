package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.DTO.StockDTO;
import com.elktibi.TFEecommerce2022.models.product.Stock;

import java.util.List;

public interface StockServiceInterface {

    Stock findStockById(Long id);
    List<Stock> findAllStock();
    void saveStocks(StockDTO stockDTO);
    void deleteStock(Long id);
}
