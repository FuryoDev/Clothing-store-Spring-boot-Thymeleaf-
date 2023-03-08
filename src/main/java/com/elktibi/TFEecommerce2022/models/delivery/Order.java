package com.elktibi.TFEecommerce2022.models.delivery;

import com.elktibi.TFEecommerce2022.models.users.User;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "deliveries")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String orderNumber;

    @NotNull
    private Calendar dateOfPurchase;

    @NotNull
    private boolean isSentToDelivery;

    @NotNull
    private double total;

    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "paymentTypeId")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "deliveryMethodId")
    private DeliveryMethod deliveryMethod;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Calendar getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Calendar dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public boolean isSentToDelivery() {
        return isSentToDelivery;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setSentToDelivery(boolean sentToDelivery) {
        isSentToDelivery = sentToDelivery;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

//    public double getTotal() {
//        return total;
//    }
//
//    public void setTotal(double total) {
//        this.total = total;
//    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
}
