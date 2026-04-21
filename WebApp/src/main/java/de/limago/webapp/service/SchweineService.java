package de.limago.webapp.service;

import de.limago.webapp.service.exception.PersonenServiceException;
import de.limago.webapp.service.model.Person;
import de.limago.webapp.service.model.Schwein;

import java.util.Optional;
import java.util.UUID;

public interface SchweineService {

    void speichern(Schwein schwein) ;
    void aendern(Schwein schwein) ;
    void loeschen(UUID uuid);
    Optional<Schwein> findeNachId(UUID uuid) ;
    Iterable<Schwein> findeAlle();
    void fuettern(UUID uuid, UUID requestId);
}
