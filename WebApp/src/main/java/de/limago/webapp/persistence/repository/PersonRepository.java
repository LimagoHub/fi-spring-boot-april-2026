package de.limago.webapp.persistence.repository;

import de.limago.webapp.persistence.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

    Iterable<PersonEntity> findByNachname(String nachname);
}
