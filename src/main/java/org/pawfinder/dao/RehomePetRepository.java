package org.pawfinder.dao;

import org.pawfinder.entity.RehomePetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RehomePetRepository extends CrudRepository<RehomePetEntity, Long> {

}