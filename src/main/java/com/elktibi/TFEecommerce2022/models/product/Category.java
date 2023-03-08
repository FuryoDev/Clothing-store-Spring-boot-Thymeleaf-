package com.elktibi.TFEecommerce2022.models.product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotNull
    @Size(min = 3, max = 13)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<TypeProduct> typeProductList;

    private String sizingType;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeProduct> getTypeProductList() {
        return typeProductList;
    }

    public void setTypeProductList(List<TypeProduct> typeProductList) {
        this.typeProductList = typeProductList;
    }

    public String getSizingType() {
        return sizingType;
    }

    public void setSizingType(String sizingType) {
        this.sizingType = sizingType;
    }
}
