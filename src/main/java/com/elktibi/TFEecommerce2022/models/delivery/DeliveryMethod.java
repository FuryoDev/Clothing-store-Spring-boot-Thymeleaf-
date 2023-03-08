package com.elktibi.TFEecommerce2022.models.delivery;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class DeliveryMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryMethodId;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(unique = true)
    private String name;

    @NotNull
    private double price;


    @OneToMany(mappedBy = "deliveryMethod")
    private List<Order> orderList;

    @PreRemove
    private void preRemove() {
        orderList.forEach( child -> child.setDeliveryMethod(null));
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long id) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
