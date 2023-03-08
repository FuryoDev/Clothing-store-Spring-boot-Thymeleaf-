package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.delivery.Address;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.repositories.AddressRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.AddressServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements AddressServiceInterface {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findAddressById(Long id) {
        return new Address();
        //return repository.findById(id);
    }

    @Override
    public Address findLastUsedAddress(User user) {
        Address address = addressRepository.findLastUsedAddress(user);
        if(address == null) {
            return new Address();
        }
        return address;
    }

    @Override
    public List<Address> findAllAddress() {
        return null;
    }

    public List<Address> findAllAddressByUser(User user) {
        return addressRepository.findAddressesByUser(user);
    }

    @Override
    public void saveAddress(User user, Address address) {
        setAsLastCreatedOrUsed(user, address);
        address.setUser(user);
        addressRepository.save(address);
    }
    @Override
    public void setAsLastCreatedOrUsed(User user, Address address) {
        List<Address> addressList = addressRepository.findAddressesByUser(user);
        for(Address a : addressList){
            a.setLastUsedOrCreated(false);
            addressRepository.save(a);
        }
        address.setLastUsedOrCreated(true);
        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        boolean exists = addressRepository.findById(id).isPresent();
        if(!exists) {
            throw new IllegalStateException("This address doesn't exists in db");
        }
        addressRepository.deleteById(id);
    }
}
