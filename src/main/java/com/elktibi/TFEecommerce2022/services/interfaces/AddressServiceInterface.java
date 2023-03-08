package com.elktibi.TFEecommerce2022.services.interfaces;


import com.elktibi.TFEecommerce2022.models.delivery.Address;
import com.elktibi.TFEecommerce2022.models.users.User;

import java.util.List;

public interface AddressServiceInterface {

    Address findAddressById(Long id);

    Address findLastUsedAddress(User user);
    List<Address> findAllAddress();
    void setAsLastCreatedOrUsed(User user, Address address);
    void saveAddress(User user, Address address);
    void deleteAddress(Long id);
}
