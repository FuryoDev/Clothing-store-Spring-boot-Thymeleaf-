package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.delivery.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {

    Optional<DeliveryMethod> findDeliveryMethodByName(String name);
}
