package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.product.TypeProduct;

import java.util.List;

public interface TypeProductServiceInterface {

    TypeProduct findTypeProductById(Long id);
    List<TypeProduct> findAllTypeProduct();
    void saveTypeProduct(TypeProduct country);
    void deleteTypeProduct(Long id);
}
