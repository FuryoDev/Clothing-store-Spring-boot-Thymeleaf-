package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.repositories.CountryRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.CountryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements CountryServiceInterface {

    @Autowired
    private CountryRepository repository;

    @Override
    public Country findCountryById(Long id) {
        Optional<Country> optionalCountry = repository.findById(id);
        boolean isPresent = optionalCountry.isPresent();
        if(!isPresent) {
            throw new IllegalStateException("This country doesn't exist in DB");
        }
        return optionalCountry.get();
    }

    @Override
    public List<Country> findAllCountry() {
        return repository.findAll();
    }

    @Override
    public void saveCountry(Country country) {
        if(country.getCountryId() == null) {
            Optional<Country> countryOptional = repository.findCountryByName(country.getName());
            if(countryOptional.isPresent()) {
                throw new IllegalStateException("This Country already exists");
            }
        }
        repository.save(country);
    }

    @Override
    public void deleteCountry(Long id) {
        boolean exists = repository.findById(id).isPresent();
        if(!exists) {
            throw new IllegalStateException("This country doesn't exists in DB");
        }
        repository.deleteById(id);
    }
}
