package me.imatveev.jooqdemo.repository.impl;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.domain.entity.GovernmentForm;
import me.imatveev.jooqdemo.domain.tables.records.CountriesRecord;
import me.imatveev.jooqdemo.repository.CountryRepository;
import me.imatveev.jooqdemo.repository.CrudRepository;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import me.imatveev.jooqdemo.domain.tables.Countries;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {
    private final DSLContext dsl;

    @Override
    public Country insert(Country country) {
        return dsl.insertInto(Countries.COUNTRIES)
                .set(dsl.newRecord(Countries.COUNTRIES, country))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + country.getId()))
                .into(Country.class);
    }

    @Override
    public Country update(Country country) {
        return dsl.update(Countries.COUNTRIES)
                .set(dsl.newRecord(Countries.COUNTRIES, country))
                .where(Countries.COUNTRIES.ID.eq(country.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + country.getId()))
                .into(Country.class);
    }

    @Override
    public Optional<Country> find(Long id) {
        return dsl.selectFrom(Countries.COUNTRIES)
                .where(Countries.COUNTRIES.ID.eq(id))
                .fetchOptional()
                .map(countriesRecord -> {
                    Country country = new Country();
                    country.setId(countriesRecord.getId());
                    country.setName(countriesRecord.getName());
                    country.setPopulation(countriesRecord.getPopulation());
                    country.setGovernmentForm(GovernmentForm.valueOf(countriesRecord.getGovernmentForm()));
                    return country;
                });
    }

    @Override
    public List<Country> findAll(Condition condition) {
        return dsl.selectFrom(Countries.COUNTRIES)
                .where(condition)
                .fetch()
                .into(Country.class);
    }

    @Override
    public Boolean delete(Long id) {
        return dsl.deleteFrom(Countries.COUNTRIES)
                .where(Countries.COUNTRIES.ID.eq(id))
                .execute() == 1;
    }
}
