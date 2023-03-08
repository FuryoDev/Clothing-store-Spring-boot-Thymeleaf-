package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import com.elktibi.TFEecommerce2022.repositories.CategoryRepository;
import com.elktibi.TFEecommerce2022.repositories.TypeProductRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.TypeProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class TypeProductService implements TypeProductServiceInterface {

    @Autowired
    private TypeProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public TypeProduct findTypeProductById(Long id) {
        Optional<TypeProduct> optionalTypeProduct = repository.findById(id);
        boolean isPresent = optionalTypeProduct.isPresent();
        if(!isPresent) {
            throw new IllegalStateException("This type of product doesn't exist in DB");
        }
        return optionalTypeProduct.get();
    }

    @Override
    public List<TypeProduct> findAllTypeProduct() {
        return repository.findAll();
    }

    public List<TypeProduct> findAllTypeProductByCategory(String categoryName) {
        Category category = categoryRepository.findCategoryByName(categoryName).get();
        return repository.findAllByCategory(category);
    }

    @Override
    public void saveTypeProduct(TypeProduct typeProduct) {
        if(typeProduct.getTypeProductId() == null) {
            Optional<TypeProduct> typeProductByName = repository.findTypeProductByName(typeProduct.getName());
            if(typeProductByName.isPresent()){
                throw new IllegalStateException("This name already exists");
            }
        }
        repository.save(typeProduct);
    }

    @Override
    public void deleteTypeProduct(Long id) {
        boolean exists = repository.findById(id).isPresent();
        if (!exists){
            throw new IllegalStateException("Tp was not found in the DB to be deleted");
        }
        repository.deleteById(id);
    }
}
