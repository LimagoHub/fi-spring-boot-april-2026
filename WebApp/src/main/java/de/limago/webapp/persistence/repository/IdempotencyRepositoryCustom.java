package de.limago.webapp.persistence.repository;

import java.util.UUID;

public interface IdempotencyRepositoryCustom {

    void tryInsert(UUID requestId);
}