package me.imatveev.jooqdemo.repository;

import me.imatveev.jooqdemo.domain.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    Optional<Country> find(Long id);

    List<Country> findAll();

    Country insert(Country t);

    Country update(Country t);

    Boolean delete(Long id);
}
