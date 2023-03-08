package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.DTO.CheckoutDTO;
import com.elktibi.TFEecommerce2022.models.delivery.Order;
import com.elktibi.TFEecommerce2022.models.users.User;

import java.util.List;

public interface OrderServiceInterface {

    Order findOrderById(Long id);

    List<Order> findAllOrders();

    List<Order> findAllOrdersByUser(User user);

    void saveOrder(CheckoutDTO checkoutDTO);
}
