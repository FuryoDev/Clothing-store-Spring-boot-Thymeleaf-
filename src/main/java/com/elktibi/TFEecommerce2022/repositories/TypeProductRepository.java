package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Repository
public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {

    Optional<TypeProduct> findTypeProductByName(String name);

    List<TypeProduct> findAllByCategory(Category category);
}
