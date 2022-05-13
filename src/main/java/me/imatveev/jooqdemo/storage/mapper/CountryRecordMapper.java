package me.imatveev.jooqdemo.storage.mapper;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.domain.tables.Cities;
import me.imatveev.jooqdemo.domain.tables.Countries;
import me.imatveev.jooqdemo.domain.tables.records.CountriesRecord;
import me.imatveev.jooqdemo.domain.CityRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryRecordMapper implements
        RecordMapper<CountriesRecord, Country>,
        RecordUnmapper<Country, CountriesRecord> {
    private final CityRepository cityRepository;
    private final DSLContext dslContext;

    @Override
    public Country map(CountriesRecord record) {
        final Country country = record.into(Country.class);
        country.setCities(cityRepository.findAll(Cities.CITIES.COUNTRY_ID.eq(country.getId())));

        return country;
    }

    @Override
    public CountriesRecord unmap(Country source) throws MappingException {
        return dslContext.newRecord(Countries.COUNTRIES, source);
    }
}
