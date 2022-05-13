package me.imatveev.jooqdemo.domain.entity;

import lombok.Data;

@Data
public class City {
    private Long id;
    private Long countryId;
    private String name;
    private Integer population;
}
