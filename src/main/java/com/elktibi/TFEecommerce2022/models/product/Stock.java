package com.elktibi.TFEecommerce2022.models.product;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @NotNull
    private String sizeName;

    private int numberItemLeft;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getNumberItemLeft() {
        return numberItemLeft;
    }

    public void setNumberItemLeft(int numberItemLeft) {
        this.numberItemLeft = numberItemLeft;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
