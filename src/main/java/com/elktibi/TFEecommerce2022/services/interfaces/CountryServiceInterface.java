package com.elktibi.TFEecommerce2022.services.interfaces;

import com.elktibi.TFEecommerce2022.models.delivery.Country;

import java.util.List;

public interface CountryServiceInterface {

    Country findCountryById(Long id);
    List<Country> findAllCountry();
    void saveCountry(Country country);
    void deleteCountry(Long id);
}
