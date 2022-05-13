package me.imatveev.jooqdemo.domain;

import me.imatveev.jooqdemo.domain.entity.City;
import org.jooq.Condition;

import java.util.List;

public interface CityRepository {
    City insert(City t);

    List<City> findAll(Condition condition);

    int delete(Condition condition);
}
