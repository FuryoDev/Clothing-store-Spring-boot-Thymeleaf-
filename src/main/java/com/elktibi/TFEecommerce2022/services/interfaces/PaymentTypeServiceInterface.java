package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;

import java.util.List;

public interface PaymentTypeServiceInterface {

    PaymentType findPaymentTypeById(Long id);
    List<PaymentType> findAllPaymentType();
    void savePaymentType(PaymentType country);
    void deletePaymentType(Long id);

}
