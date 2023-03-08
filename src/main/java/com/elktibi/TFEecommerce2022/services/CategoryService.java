package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.repositories.CategoryRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        boolean isPresent = optionalCategory.isPresent();
        if(!isPresent) {
            throw new IllegalStateException("This category doesn't exist in DB");
        }
        return optionalCategory.get();
    }

    @Override
    public List<Category> findAllCategory() {
        return repository.findAll();
    }

    @Override
    public void saveCategory(Category category) {
        if(category.getCategoryId() == null) {
            Optional<Category> categoryOptional = repository.findCategoryByName(category.getName());
            if(categoryOptional.isPresent()) {
                throw new IllegalStateException("This name is already taken");
            }
        }
        repository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        boolean exists = repository.findById(id).isPresent();
        if(!exists) {
            throw new IllegalStateException("Category wasn't found in DB");
        }
        repository.deleteById(id);
    }
}
