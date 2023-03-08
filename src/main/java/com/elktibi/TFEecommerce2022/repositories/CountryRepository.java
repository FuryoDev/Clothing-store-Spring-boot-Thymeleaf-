package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findCountryByName(String name);
}
