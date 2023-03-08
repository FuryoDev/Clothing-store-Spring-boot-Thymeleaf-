package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    Optional<PaymentType> findPaymentTypeByName(String name);
}
