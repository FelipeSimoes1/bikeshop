package com.maisprati.bikeshop.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BikeModel {

    MTB("Mountain Bike"),
    ROAD("Road Bike"),
    GRAVEL("Gravel Bike"),
    URBAN("Urban Bike");

    private final String description;
}
