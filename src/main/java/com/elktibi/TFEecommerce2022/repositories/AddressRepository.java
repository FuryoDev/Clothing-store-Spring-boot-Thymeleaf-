package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.delivery.Address;
import com.elktibi.TFEecommerce2022.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAddressesByUser(User user);

    @Query("SELECT a from Address a WHERE a.user = :user AND a.lastUsedOrCreated = true")
    Address findLastUsedAddress(User user);
}
