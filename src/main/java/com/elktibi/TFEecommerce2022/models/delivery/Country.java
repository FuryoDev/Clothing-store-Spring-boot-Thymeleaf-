package com.elktibi.TFEecommerce2022.models.delivery;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "country")
    private List<Address> addressList;

    @PreRemove
    private void preRemove() {
        addressList.forEach( child -> child.setCountry(null));
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
