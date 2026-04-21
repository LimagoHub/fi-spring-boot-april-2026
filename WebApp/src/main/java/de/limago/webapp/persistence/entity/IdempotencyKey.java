package de.limago.webapp.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID requestId;

    private Instant createdAt = Instant.now();

    protected IdempotencyKey() {}

    public IdempotencyKey(UUID requestId) {
        this.requestId = requestId;
    }
}