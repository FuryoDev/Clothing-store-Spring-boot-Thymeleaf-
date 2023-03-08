package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.Color;
import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import com.elktibi.TFEecommerce2022.services.TypeProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//
////    @Query("SELECT * FROM xxxx")
//    List<Product> findByCategory(String category);

    List<Product> findAllByTypeProductAndColor(TypeProduct typeProduct, Color color);

    @Query("SELECT p from Product p WHERE p.color =:color AND p.typeProduct IN (SELECT tp from TypeProduct tp WHERE tp.category = :category)")
    List<Product> findByColorAndCategory(Color color, Category category);

    @Query("SELECT p from Product p ORDER BY p.dateOfCreation DESC")
    List<Product> findLastProductCreated(int amount);

    List<Product> findAllByTypeProduct(TypeProduct typeProduct);

    Optional<Product> findProductByName(String name);
}
