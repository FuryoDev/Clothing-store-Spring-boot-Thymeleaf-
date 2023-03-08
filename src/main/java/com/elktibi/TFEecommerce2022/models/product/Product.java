package com.elktibi.TFEecommerce2022.models.product;


import com.elktibi.TFEecommerce2022.models.delivery.OrderItem;
import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull
    @Size(min = 5, max = 35)
    @Column(unique = true)
    private String name;

    @NotNull
    @DecimalMin("0.01")
    private double price;

    private String description;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "typeProductId")
    private TypeProduct typeProduct;

    @ManyToOne( fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "colorId")
    private Color color;

    @OneToMany (mappedBy = "product", cascade = CascadeType.ALL)
    private List<Stock> stocks;

    //For orderItem, we don't want bi-directional relations, so we don't give the getters/setters
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    private String imagePath;

    private Calendar dateOfCreation;

    @PreRemove
    private void preRemoveForOrderItems() {
        orderItems.forEach( child -> child.setProduct(null));
    }



    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Calendar getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Calendar dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
