package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.delivery.DeliveryMethod;
import com.elktibi.TFEecommerce2022.repositories.DeliveryMethodRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.DeliveryMethodServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryMethodService implements DeliveryMethodServiceInterface {

    @Autowired
    private DeliveryMethodRepository repository;

    @Override
    public DeliveryMethod findDeliveryMethodById(Long id) {
        Optional<DeliveryMethod> optionalDeliveryMethod = repository.findById(id);
        boolean isPresent = optionalDeliveryMethod.isPresent();
        if(!isPresent) {
            throw new IllegalStateException("This delivery method doesn't exist in DB");
        }
        return optionalDeliveryMethod.get();
    }

    @Override
    public List<DeliveryMethod> findAllDeliveryMethod() {
        return repository.findAll();
    }

    @Override
    public void saveDeliveryMethod(DeliveryMethod deliveryMethod) {
        if(deliveryMethod.getDeliveryMethodId() == null) {
            Optional<DeliveryMethod> deliveryMethodOptional = repository.findDeliveryMethodByName(deliveryMethod.getName());
            if(deliveryMethodOptional.isPresent()) {
                throw new IllegalStateException("This DM already exists");
            }
        }
        repository.save(deliveryMethod);
    }

    @Override
    public void deleteDeliveryMethod(Long id) {
        boolean exists = repository.findById(id).isPresent();
        if(!exists) {
            throw new IllegalStateException("This Dm wasn't found in DB");
        }
        repository.deleteById(id);
    }
}
