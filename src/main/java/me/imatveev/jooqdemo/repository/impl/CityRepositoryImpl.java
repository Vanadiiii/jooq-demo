package me.imatveev.jooqdemo.repository.impl;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.entity.City;
import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.domain.tables.Cities;
import me.imatveev.jooqdemo.domain.tables.Countries;
import me.imatveev.jooqdemo.repository.CityRepository;
import me.imatveev.jooqdemo.repository.mapper.CityRecordMapper;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CityRepositoryImpl implements CityRepository {
    private final DSLContext dsl;
    private final CityRecordMapper recordMapper;

    @Override
    public City insert(City city) {
        return dsl.insertInto(Cities.CITIES)
                .set(recordMapper.unmap(city))
                .returning()
                .fetchOptional()
                .map(recordMapper::map)
                .orElseThrow(() -> new DataAccessException("Error inserting city: " + city.getId()));
    }

    @Override
    public List<City> findAll(Condition condition) {
        return dsl.selectFrom(Cities.CITIES)
                .where(condition)
                .fetch()
                .into(City.class);
    }

    @Override
    public int delete(Condition condition) {
        return dsl.deleteFrom(Cities.CITIES)
                .where(condition)
                .execute();
    }
}
