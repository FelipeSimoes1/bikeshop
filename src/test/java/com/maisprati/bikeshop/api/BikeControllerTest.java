package com.maisprati.bikeshop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maisprati.bikeshop.api.request.PricePatchRequest;
import com.maisprati.bikeshop.domain.Bike;
import com.maisprati.bikeshop.domain.BikeRepository;
import com.maisprati.bikeshop.exception.BadRequestException;
import com.maisprati.bikeshop.service.BikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BikeController.class)
class BikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BikeService bikeService;

    @MockBean
    private BikeRepository bikeRepository;

    private List<Bike> bikes = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Bike bikeTester = new Bike();
        bikeTester.setId(1L);
        bikeTester.setName("Sense Fun");
        bikeTester.setPrice(BigDecimal.valueOf(2800));
        bikeTester.setDescription("24 marchas, aro 29");
        bikeTester.setPurchaseDate(LocalDate.now());
        bikes.add(bikeTester);

        Bike bikeNext = new Bike();
        bikeNext.setId(21L);
        bikeNext.setName("Caloi Elite");
        bikeNext.setDescription("27 marchas, aro 26");
        bikeNext.setPurchaseDate(LocalDate.now());
        bikes.add(bikeNext);
    }

    @Test
    void save_ReturnsError_WhenSavedWithoutId() throws Exception {
        Bike bikeWithoutId = new Bike();
        bikeWithoutId.setName("Monark Barra Forte");
        bikeWithoutId.setPurchaseDate(LocalDate.now());

        mockMvc.perform(post("/bikes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bikeWithoutId)))
                .andExpect(status().isCreated());
    }

    @Test
    void save_ReturnsError_WhenSavedWithoutName() throws Exception {
        Bike bikeWithoutName = new Bike();
        bikeWithoutName.setName(null);
        bikeWithoutName.setPurchaseDate(LocalDate.now());

        mockMvc.perform(post("/bikes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bikeWithoutName)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAll_ReturnsListBikes_WhenSuccessful() throws Exception {
        when(bikeService.findAll()).thenReturn(bikes);
        mockMvc.perform(MockMvcRequestBuilders.get("/bikes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].name", is("Caloi Elite")));
    }

    @Test
    void findAll_ReturnsEmptyListBikes_WhenSuccessfull() throws Exception {
        when(bikeService.findAll()).thenReturn(bikes);
        bikes.clear();
        mockMvc.perform(MockMvcRequestBuilders.get("/bikes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void delete_ReturnsNoContent_WhenDeleteBikeWithValidId() throws Exception {
        Bike newBike = new Bike();
        newBike.setId(12L);
        newBike.setName("Tsw Jumper");
        newBike.setPurchaseDate(LocalDate.now());

        doNothing().when(bikeService).delete(newBike.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/bikes/" + newBike.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_ReturnsBadRequest_WhenDeleteBikeWithInvalidId() throws Exception {
        doThrow(BadRequestException.class).when(bikeService).delete(18L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/bikes/18")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById_ReturnsBike_WhenFindBikeWithValidId() throws Exception {
        Bike newBike = new Bike();
        newBike.setId(12L);
        newBike.setName("Tsw Jumper");
        newBike.setPurchaseDate(LocalDate.now());

        when(bikeService.findById(12L)).thenReturn(Optional.of(newBike));

        mockMvc.perform(MockMvcRequestBuilders.get("/bikes/" + newBike.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(12)))
                .andExpect(jsonPath("$.name",is("Tsw Jumper")));
    }

    @Test
    void findById_ReturnsBadRequest_WhenFindBikeWithInvalidId() throws Exception {
        doThrow(BadRequestException.class).when(bikeService).findById(18L);

        mockMvc.perform(MockMvcRequestBuilders.get("/bikes/18")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_ReturnsSuccess_WhenUpdatedValidBike() throws Exception {

        Bike newBike = new Bike();
        newBike.setId(12L);
        newBike.setName("Tsw Jumper");
        newBike.setPurchaseDate(LocalDate.now());

        Bike bikeUpdated = new Bike();
        bikeUpdated.setName("Teste");
        bikeUpdated.setPrice(BigDecimal.valueOf(3000));

        when(bikeService.findById(12L)).thenReturn(Optional.of(newBike));
        when(bikeService.save(any(Bike.class))).thenReturn(bikeUpdated);

        mockMvc.perform(put("/bikes/12")
                .content(objectMapper.writeValueAsString(newBike))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePrice_ReturnsSuccess_WhenUpdatedValidBike() throws Exception {

        Bike newBike = new Bike();
        newBike.setId(12L);
        newBike.setName("Tsw Jumper");
        newBike.setPrice(BigDecimal.valueOf(1299));
        newBike.setPurchaseDate(LocalDate.now());

        PricePatchRequest newPrice = PricePatchRequest.builder().price(BigDecimal.valueOf(3800)).build();

        when(bikeService.findById(12L)).thenReturn(Optional.of(newBike));
        when(bikeService.priceUpdate(12L, newPrice)).thenReturn(newBike);

        mockMvc.perform(patch("/bikes/12")
                .content(objectMapper.writeValueAsString(newPrice))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}