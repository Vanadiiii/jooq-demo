package me.imatveev.jooqdemo.repository.impl;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.entity.City;
import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.repository.CityRepository;
import me.imatveev.jooqdemo.repository.CountryRepository;
import me.imatveev.jooqdemo.repository.mapper.CountryRecordMapper;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.imatveev.jooqdemo.domain.tables.Cities.CITIES;
import static me.imatveev.jooqdemo.domain.tables.Countries.COUNTRIES;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {
    private final DSLContext dsl;
    private final CountryRecordMapper recordMapper;
    private final CityRepository cityRepository;

    @Override
    public Optional<Country> find(Long id) {
        return dsl.selectFrom(
                        COUNTRIES.join(CITIES)
                                .on(COUNTRIES.ID.eq(CITIES.COUNTRY_ID))
                )
                .where(COUNTRIES.ID.eq(id))
                .fetchGroups(
                        record -> record.into(COUNTRIES).into(Country.class),
                        record -> record.into(CITIES).into(City.class)
                )
                .entrySet()
                .stream()
                .map(entry -> {
                    final Country country = entry.getKey();
                    country.setCities(entry.getValue());
                    return country;
                })
                .findFirst();
    }

    @Override
    public List<Country> findAll() {
        return dsl.selectFrom(
                        COUNTRIES.join(CITIES)
                                .on(COUNTRIES.ID.eq(CITIES.COUNTRY_ID))
                )
                .fetchGroups(
                        record -> record.into(COUNTRIES).into(Country.class),
                        record -> record.into(CITIES).into(City.class)
                )
                .entrySet()
                .stream()
                .map(entry -> {
                    final Country country = entry.getKey();
                    country.setCities(entry.getValue());
                    return country;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Country insert(Country country) {
        return dsl.transactionResult(conf -> {
                    final Country savedCountry = conf.dsl()
                            .insertInto(COUNTRIES)
                            .set(recordMapper.unmap(country))
                            .returning()
                            .fetchOptional()
                            .map(recordMapper::map)
                            .orElseThrow(() -> new DataAccessException("Error inserting entity: " + country.getId()));

                    final List<City> savedCities = country.getCities()
                            .stream()
                            .peek(city -> city.setCountryId(savedCountry.getId()))
                            .map(cityRepository::insert)
                            .toList();

                    savedCountry.setCities(savedCities);

                    return savedCountry;
                }
        );
    }

    @Override
    public Country update(Country country) {
        return dsl.transactionResult(conf -> {
            final Country updatedCountry = conf.dsl()
                    .update(COUNTRIES)
                    .set(recordMapper.unmap(country))
                    .where(COUNTRIES.ID.eq(country.getId()))
                    .returning()
                    .fetchOptional()
                    .map(recordMapper::map)
                    .orElseThrow(() -> new DataAccessException("Error updating entity: " + country.getId()));

            cityRepository.delete(CITIES.COUNTRY_ID.eq(country.getId()));

            final List<City> updatedCities = country.getCities()
                    .stream()
                    .peek(city -> city.setCountryId(updatedCountry.getId()))
                    .map(cityRepository::insert)
                    .toList();

            updatedCountry.setCities(updatedCities);

            return updatedCountry;
        });
    }

    @Override
    public Boolean delete(Long id) {
        return dsl.transactionResult(conf -> {
            final boolean isCountryDeleted = dsl.deleteFrom(COUNTRIES)
                    .where(COUNTRIES.ID.eq(id))
                    .execute() == 1;

            cityRepository.delete(CITIES.COUNTRY_ID.eq(id));

            return isCountryDeleted;
        });
    }
}
