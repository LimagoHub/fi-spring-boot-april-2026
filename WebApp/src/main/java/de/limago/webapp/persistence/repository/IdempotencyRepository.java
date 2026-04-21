package de.limago.webapp.persistence.repository;

import de.limago.webapp.persistence.entity.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdempotencyRepository extends IdempotencyRepositoryCustom, JpaRepository<IdempotencyKey, UUID> {}