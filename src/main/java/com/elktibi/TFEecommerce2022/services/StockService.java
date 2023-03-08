package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.DTO.StockDTO;
import com.elktibi.TFEecommerce2022.models.product.Stock;
import com.elktibi.TFEecommerce2022.repositories.StockRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.StockServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService implements StockServiceInterface {

    @Autowired
    private StockRepository stockRepository;
    @Override
    public Stock findStockById(Long id) {
        return stockRepository.findById(id).get();
    }

    @Override
    public List<Stock> findAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public void saveStocks(StockDTO stockDTO) {
        for(Stock s : stockDTO.getStocks()){
            Stock tempStock = findStockById(s.getStockId());
            tempStock.setNumberItemLeft(s.getNumberItemLeft());
            stockRepository.save(tempStock);
        }
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
