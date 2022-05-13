package me.imatveev.jooqdemo.storage.mapper;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.entity.City;
import me.imatveev.jooqdemo.domain.tables.Cities;
import me.imatveev.jooqdemo.domain.tables.records.CitiesRecord;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityRecordMapper implements
        RecordMapper<CitiesRecord, City>,
        RecordUnmapper<City, CitiesRecord> {
    private final DSLContext dslContext;

    @Override
    public City map(CitiesRecord record) {
        return record.into(City.class);
    }

    @Override
    public CitiesRecord unmap(City source) throws MappingException {
        return dslContext.newRecord(Cities.CITIES, source);
    }
}
