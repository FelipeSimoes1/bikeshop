package com.maisprati.bikeshop.service;

import com.maisprati.bikeshop.domain.Bike;
import com.maisprati.bikeshop.domain.BikeRepository;
import com.maisprati.bikeshop.api.request.PricePatchRequest;
import com.maisprati.bikeshop.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BikeService {

    private BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    public Optional<Bike> findById(Long id) {
        verifyIfExists("find",id);
        return bikeRepository.findById(id);
    }

    public Bike save(Bike bikeToSave) throws BadRequestException {
        if (bikeToSave.getId() != null) {
            throw new BadRequestException("Não é possível incluir uma bike com o ID informado.");
        }
        return bikeRepository.save(bikeToSave);
    }

    public Bike update(Long id, Bike bikeToUpdate) {
        Bike bikeUpdated = verifyIfExists("update",id);

        bikeUpdated.setName(bikeToUpdate.getName());
        bikeUpdated.setDescription(bikeToUpdate.getDescription());
        bikeUpdated.setBikeModel(bikeToUpdate.getBikeModel());
        bikeUpdated.setPrice(bikeToUpdate.getPrice());
        bikeUpdated.setPurchaseDate(bikeToUpdate.getPurchaseDate());
        bikeUpdated.setStore(bikeToUpdate.getStore());
        bikeUpdated.setClient(bikeToUpdate.getClient());

        return bikeRepository.save(bikeUpdated);
    }

    public void delete(Long id) {
        verifyIfExists("delete",id);
        bikeRepository.deleteById(id);
    }

    public Bike priceUpdate(Long id, PricePatchRequest newPrice) {
        Bike bikeToPatch = verifyIfExists("update price",id);

        bikeToPatch.setPrice(newPrice.getPrice());

        return bikeRepository.save(bikeToPatch);
    }

    public Bike verifyIfExists(String action, Long id) throws BadRequestException {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(action, id));
    }


}
