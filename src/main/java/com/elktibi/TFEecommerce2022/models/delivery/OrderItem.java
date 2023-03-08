package com.elktibi.TFEecommerce2022.models.delivery;

import com.elktibi.TFEecommerce2022.models.product.Product;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @NotNull
    private double priceWhenBought;

    @NotNull
    private int quantity;

    @NotNull
    private String size;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "orderId")
    private Order order;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long id) {
        this.orderItemId = orderItemId;
    }

    public double getPriceWhenBought() {
        return priceWhenBought;
    }

    public void setPriceWhenBought(double priceWhenBought) {
        this.priceWhenBought = priceWhenBought;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
