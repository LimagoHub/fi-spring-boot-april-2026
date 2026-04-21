package de.limago.webapp.persistence.repository;

import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.persistence.entity.TinyPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PersonRepository extends CrudRepository<PersonEntity, UUID>, PersonenCustomRepository {

    Iterable<PersonEntity> findByNachname(String nachname);

    @Query("select new de.limago.webapp.persistence.entity.TinyPerson(p.id, p.nachname) from PersonEntity p")
    Iterable<TinyPerson> xyz();

    Iterable<TinyPerson> findAllProjectBy();
}
