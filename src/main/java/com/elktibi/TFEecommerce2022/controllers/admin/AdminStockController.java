package com.elktibi.TFEecommerce2022.controllers.admin;

import com.elktibi.TFEecommerce2022.DTO.StockDTO;
import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.product.Stock;
import com.elktibi.TFEecommerce2022.services.ProductService;
import com.elktibi.TFEecommerce2022.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminStockController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @GetMapping("/stock-form")
    public String stockForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        StockDTO stockDTO = new StockDTO();
        Product product = productService.findProductById(id);
        List<Stock> stocks = product.getStocks();
        stockDTO.setStocks(stocks);

        model.addAttribute("stockDTO", stockDTO);
        return "admin/forms/stock-form";
    }

    @PostMapping("/saveStock")
    public String saveStock(Model model, StockDTO stockDTO, BindingResult bindingResult) {
        stockService.saveStocks(stockDTO);
        return "admin/dashboards/stock-admin";
    }

//    @PutMapping("/editStock")
//    public String editStock(Model model, @Valid Stock stock, BindingResult bindingResult) {
//        if(!bindingResult.hasErrors() && country.getCountryId() == null) {
////            sto.saveCountry(country);
//        }
//        return "admin/forms/stock-form";
//    }

    public String deleteStock() {
        return "x";
    }
}
