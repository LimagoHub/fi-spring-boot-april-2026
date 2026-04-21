package de.limago.webapp.persistence.repository;

import de.limago.webapp.persistence.entity.IdempotencyKey;
import de.limago.webapp.service.exception.AlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class IdempotencyRepositoryCustomImpl implements IdempotencyRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void tryInsert(UUID requestId) {
        try {
            em.persist(new IdempotencyKey(requestId));
            em.flush(); // sofort flushen, damit die DB-Exception hier fliegt

        } catch (PersistenceException e) {
            throw new AlreadyExistsException("Anfrage wurde bereits gesendet");
        }
    }
}