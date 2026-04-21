package de.limago.webapp.persistence.repository;

import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.service.exception.AlreadyExistsException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PersonenCustomRepositoryImpl implements PersonenCustomRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void onlySave(final PersonEntity personEntity) {
        try {
            em.persist(personEntity);
            em.flush();
        } catch (EntityExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        }
    }
}
