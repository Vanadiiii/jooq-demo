package me.imatveev.jooqdemo.domain;

import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.domain.exception.CountryNotFoundException;

import java.util.List;

public interface CountryService {
    List<Country> findAll();

    Country findById(Long id) throws CountryNotFoundException;

    long calculatePopulation(Long id);

    Country save(Country country);

    void deleteById(Long id) throws CountryNotFoundException;

    Country update(Country country);
}
