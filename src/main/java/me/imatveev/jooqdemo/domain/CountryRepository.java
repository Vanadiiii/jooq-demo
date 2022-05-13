package me.imatveev.jooqdemo.domain;

import me.imatveev.jooqdemo.domain.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    Optional<Country> find(Long id);

    List<Country> findAll();

    long calculatePopulation(Long id);

    Country insert(Country t);

    Country update(Country t);

    boolean delete(Long id);
}
