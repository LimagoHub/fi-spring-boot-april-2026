package de.limago.webapp.service.event;

import java.util.UUID;

public record PersonCreatedEvent(UUID id, String vorname, String nachname) {
}
