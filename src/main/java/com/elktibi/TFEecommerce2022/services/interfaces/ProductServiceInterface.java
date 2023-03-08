package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.product.Stock;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceInterface {

    Product findProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String Category);
    void saveProduct(Product product, MultipartFile image);
    void deleteProduct(Long ProductId);

    List<Product> getNewProducts(int amount);
}
