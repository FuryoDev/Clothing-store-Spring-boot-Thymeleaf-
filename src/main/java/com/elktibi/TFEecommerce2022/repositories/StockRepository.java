package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


//    @Query("SELECT * FROM Stock WHERE xxxx")
//    List<Stock> findStockByProduct(Product product);

    Stock findBySizeNameAndProduct(String size, Product product);
}
