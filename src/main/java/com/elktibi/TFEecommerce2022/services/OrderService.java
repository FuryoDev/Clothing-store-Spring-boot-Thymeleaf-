package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.DTO.CheckoutDTO;
import com.elktibi.TFEecommerce2022.models.delivery.Order;
import com.elktibi.TFEecommerce2022.models.delivery.OrderItem;
import com.elktibi.TFEecommerce2022.models.product.Stock;
import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.repositories.CartRepository;
import com.elktibi.TFEecommerce2022.repositories.OrderRepository;
import com.elktibi.TFEecommerce2022.repositories.StockRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private StockRepository stockRepository;


    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findAllOrders() {
        return null;
    }

    @Override
    public List<Order> findAllOrdersByUser(User user) {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrder(CheckoutDTO checkoutDTO) {
        Order order = new Order();

        order.setDateOfPurchase(Calendar.getInstance());
        order.setDeliveryMethod(checkoutDTO.getDeliveryMethod());
        order.setPaymentType(checkoutDTO.getPaymentType());
        order.setSentToDelivery(false);
        order.setTotal(checkoutDTO.getCart().getTotal());

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem ci: checkoutDTO.getCart().getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(ci.getProduct());
            orderItem.setSize(ci.getSize());
            orderItem.setQuantity(ci.getQuantity());
            orderItem.setPriceWhenBought(ci.getProduct().getPrice());
            orderItems.add(orderItem);
            orderItem.setOrder(order);
        }
        order.setUser(checkoutDTO.getCart().getUser());
        order.setOrderItems(orderItems);
        subtractStocksFromOrder(orderItems);
        orderRepository.save(order);
    }


    public void subtractStocksFromOrder(List<OrderItem> items) {
        for(OrderItem oi: items) {
            Stock stock = stockRepository.findBySizeNameAndProduct(oi.getSize(), oi.getProduct());
            int newQuantity = stock.getNumberItemLeft() - oi.getQuantity();
            stock.setNumberItemLeft(newQuantity);
            stockRepository.save(stock);
        }
    }
}
