package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.product.Category;

import java.util.List;

public interface CategoryServiceInterface {

    Category findCategoryById(Long id);
    List<Category> findAllCategory();
    void saveCategory(Category category);
    void deleteCategory(Long id);
}
