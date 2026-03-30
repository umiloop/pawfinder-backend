package org.pawfinder.dao;

import org.pawfinder.entity.RehomePetEntity;
import org.pawfinder.entity.ShelterPetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterPetRepository extends CrudRepository<ShelterPetEntity, Long> {

}
