package com.maisprati.bikeshop.api;

import com.maisprati.bikeshop.domain.Bike;
import com.maisprati.bikeshop.exception.BadRequestException;
import com.maisprati.bikeshop.service.BikeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @ApiOperation("Busca todas as bikes.")
    @GetMapping
    public ResponseEntity<List<Bike>> findAll(){
        return ResponseEntity.ok(bikeService.findAll());
    }

    @ApiOperation("Busca uma bike pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bike>> findById(@PathVariable Long id){
        return ResponseEntity.ok(bikeService.findById(id));
    }

    @ApiOperation("Cria uma nova bike.")
    @PostMapping("")
    public ResponseEntity<Bike> save(@RequestBody @Valid Bike bikeToSave) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(bikeService.save(bikeToSave));
    }

    @ApiOperation("Atualiza todos os atributos de uma bike.")
    @PutMapping("/{id}")
    public ResponseEntity<Bike> update(@PathVariable Long id, @RequestBody Bike bikeToUpdate){
        return ResponseEntity.ok(bikeService.update(id,bikeToUpdate));
    }

    @ApiOperation("Apaga uma bike pelo ID.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        bikeService.delete(id);
    }

}
