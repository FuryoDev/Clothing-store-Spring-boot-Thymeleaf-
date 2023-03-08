package com.elktibi.TFEecommerce2022.DTO;

import com.elktibi.TFEecommerce2022.models.delivery.Address;
import com.elktibi.TFEecommerce2022.models.delivery.Order;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserInfoDTO {

    private String name;
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;
    public List<Address> addressList;
    public List<Order> orderList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
