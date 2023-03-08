package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.delivery.Order;
import com.elktibi.TFEecommerce2022.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);
}
