package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.product.Color;

import java.util.List;

public interface ColorServiceInterface {

    Color findColorById(Long id);
    List<Color> findAllColor();
    void saveColor(Color country);
    void deleteColor(Long id);
}
