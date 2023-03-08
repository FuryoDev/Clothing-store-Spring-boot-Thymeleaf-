package com.elktibi.TFEecommerce2022.models.delivery;

import com.elktibi.TFEecommerce2022.models.users.User;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotNull
    private String name;

    @NotNull
    private String zipCode;

    @NotNull
    private String city;

    @OneToOne
    @JoinColumn(name = "countryId")
    private Country country;


    @NotNull
    private boolean lastUsedOrCreated;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isLastUsedOrCreated() {
        return lastUsedOrCreated;
    }

    public void setLastUsedOrCreated(boolean lastUsedOrCreated) {
        this.lastUsedOrCreated = lastUsedOrCreated;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country=" + country +
                '}';
    }
}
