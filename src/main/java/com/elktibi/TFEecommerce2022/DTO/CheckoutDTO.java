package com.elktibi.TFEecommerce2022.DTO;

import com.elktibi.TFEecommerce2022.models.delivery.Address;
import com.elktibi.TFEecommerce2022.models.delivery.DeliveryMethod;
import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import com.elktibi.TFEecommerce2022.models.shop.Cart;

public class CheckoutDTO {

    private Cart cart;
    private DeliveryMethod deliveryMethod;
    private Address address;
    private PaymentType paymentType;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
