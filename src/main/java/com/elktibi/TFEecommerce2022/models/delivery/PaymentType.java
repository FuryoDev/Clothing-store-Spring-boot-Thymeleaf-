package com.elktibi.TFEecommerce2022.models.delivery;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class PaymentType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTypeId;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> orderList;

    @PreRemove
    private void preRemove() {
        orderList.forEach( child -> child.setPaymentType(null));
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
