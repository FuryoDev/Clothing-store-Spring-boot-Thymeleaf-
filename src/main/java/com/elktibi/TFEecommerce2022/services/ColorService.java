package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.product.Color;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import com.elktibi.TFEecommerce2022.repositories.ColorRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.ColorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService implements ColorServiceInterface {

    @Autowired
    private ColorRepository repository;

    @Override
    public Color findColorById(Long id) {
        Optional<Color> optionalColor = repository.findById(id);
        boolean isPresent = optionalColor.isPresent();
        if(!isPresent) {
            throw new IllegalStateException("This color doesn't exist in DB");
        }
        return optionalColor.get();
    }

    @Override
    public List<Color> findAllColor() {
        return repository.findAll();
    }

    @Override
    public void saveColor(Color color) {
        if(color.getColorId() == null) {
            Optional<Color> colorOptional = repository.findColorByName(color.getName());
            if(colorOptional.isPresent()){
                throw new IllegalStateException("This Color already exists");
            }
        }
        repository.save(color);
    }

    @Override
    public void deleteColor(Long id) {
        boolean exists = repository.findById(id).isPresent();
        if(!exists){
            throw new IllegalStateException("This Color wasn't found in DB");
        }
        repository.deleteById(id);
    }
}
