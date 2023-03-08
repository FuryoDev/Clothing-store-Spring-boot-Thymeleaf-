package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.models.delivery.DeliveryMethod;

import java.util.List;

public interface DeliveryMethodServiceInterface {

    DeliveryMethod findDeliveryMethodById(Long id);
    List<DeliveryMethod> findAllDeliveryMethod();
    void saveDeliveryMethod(DeliveryMethod deliveryMethod);
    void deleteDeliveryMethod(Long id);
}
