package com.maisprati.bikeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maisprati.bikeshop.enuns.BikeModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Nome da Bike", example = "Sense Fun 2021")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @ApiModelProperty(value = "Descrição da Bike", example = "Freios hidráulicos, quadro em carbono, aro 29")
    @Size(min = 2, max = 100)
    private String description;

    @ApiModelProperty(value = "Modelo da Bike", example = "MTB")
    @Enumerated(EnumType.STRING)
    private BikeModel bikeModel;

    @ApiModelProperty(value = "Preço da Bike", example = "2600")
    private BigDecimal price;

    @ApiModelProperty(value = "Data da compra da Bike", example = "2021-02-12")
    private LocalDate purchaseDate;

    @ApiModelProperty(value = "Nome da loja onde foi comprada", example = "Super Bikes")
    @Size(min = 2, max = 100)
    private String store;

    @ApiModelProperty(value = "Nome do cliente que comprou", example = "Emerson Silva")
    @Size(min = 2, max = 100)
    private String client;
}
