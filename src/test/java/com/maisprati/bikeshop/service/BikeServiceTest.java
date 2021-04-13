package com.maisprati.bikeshop.service;

import com.maisprati.bikeshop.domain.Bike;
import com.maisprati.bikeshop.domain.BikeRepository;
import com.maisprati.bikeshop.exception.BadRequestException;
import com.maisprati.bikeshop.util.BikeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class BikeServiceTest {

    @InjectMocks
    private BikeService bikeService;

    @Mock
    private BikeRepository bikeRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(bikeRepositoryMock.findAll())
                .thenReturn(List.of(BikeCreator.createValidBike()));

        BDDMockito.when(bikeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(BikeCreator.createValidBike()));

        BDDMockito.when(bikeRepositoryMock.save(ArgumentMatchers.any(Bike.class)))
                .thenReturn(BikeCreator.createValidBikeWithoutId());

        BDDMockito.doNothing().when(bikeRepositoryMock).delete(ArgumentMatchers.any(Bike.class));
    }

    @Test
    void findAll_ReturnsListBikes_WhenSuccessful(){
        String expectedName = BikeCreator.createValidBike().getName();
        List<Bike> bikes = bikeService.findAll();

        Assertions.assertThat(bikes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(bikes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findById_ReturnsBike_WhenSuccessful(){
        Long expectedId = BikeCreator.createValidBike().getId();

        Optional<Bike> bike = bikeService.findById(1L);

        Assertions.assertThat(bike).isNotNull();

        Assertions.assertThat(bike.get().getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void findById_ReturnsBadRequestException_WhenNotFound(){
        BDDMockito.when(bikeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> bikeService.findById(1L));
    }

    @Test
    void save_ReturnsBike_WhenSuccessful(){
        Bike bike = bikeService.save(BikeCreator.createValidBikeWithoutId());
        Assertions.assertThat(bike).isNotNull().isEqualTo(BikeCreator.createValidBikeWithoutId());
    }

    @Test
    void delete_RemovesBike_WhenSuccessful(){
        Assertions.assertThatCode(() ->bikeService.delete(1L))
                .doesNotThrowAnyException();
    }

}

