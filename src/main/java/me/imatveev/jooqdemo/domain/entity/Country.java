package me.imatveev.jooqdemo.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class Country {
    private Long id;
    private String name;
    private GovernmentForm governmentForm;
    private Integer population;
    private List<City> cities;
}
