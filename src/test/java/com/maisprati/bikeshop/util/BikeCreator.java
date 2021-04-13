package com.maisprati.bikeshop.util;

import com.maisprati.bikeshop.domain.Bike;
import com.maisprati.bikeshop.enuns.BikeModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BikeCreator {

    public static Bike createValidBike(){
        return Bike.builder()
                .id(1L)
                .name("Sense Evo")
                .bikeModel(BikeModel.MTB)
                .price(BigDecimal.valueOf(2800))
                .description("24 marchas, aro 29")
                .purchaseDate(LocalDate.now())
                .client("Adriana")
                .store("BikeTools")
                .build();
    }

    public static Bike createValidBikeWithoutId(){
        return Bike.builder()
                .name("Sense Evo")
                .bikeModel(BikeModel.MTB)
                .price(BigDecimal.valueOf(2800))
                .description("24 marchas, aro 29")
                .purchaseDate(LocalDate.now())
                .client("Adriana")
                .store("BikeTools")
                .build();
    }
}
