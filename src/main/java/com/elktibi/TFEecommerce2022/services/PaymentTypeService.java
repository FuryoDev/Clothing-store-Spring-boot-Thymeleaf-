package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import com.elktibi.TFEecommerce2022.repositories.PaymentTypeRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.PaymentTypeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentTypeService implements PaymentTypeServiceInterface {

    @Autowired
    private PaymentTypeRepository repository;
    @Override
    public PaymentType findPaymentTypeById(Long id) {
        Optional<PaymentType> optionalPaymentType = repository.findById(id);
        boolean isPresent = optionalPaymentType.isPresent();
        if(!isPresent) {
            throw new IllegalStateException("This payment type doesn't exist in DB");
        }
        return optionalPaymentType.get();
    }

    @Override
    public List<PaymentType> findAllPaymentType() {
        return repository.findAll();
    }

    @Override
    public void savePaymentType(PaymentType paymentType) {
        Optional<PaymentType> paymentTypeOptional = repository.findPaymentTypeByName(paymentType.getName());
        if(paymentTypeOptional.isPresent()) {
            throw new IllegalStateException("This payment type already exists");
        }
        repository.save(paymentType);
    }

    @Override
    public void deletePaymentType(Long id) {
        boolean exists = repository.findById(id).isPresent();
        if(!exists) {
            throw new IllegalStateException("This payment Type wasbt found ind DB");
        }
        repository.deleteById(id);
    }
}
