package de.limago.webapp.service;

import de.limago.webapp.service.exception.PersonenServiceException;
import de.limago.webapp.service.model.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonenService {

    void speichern(Person person) throws PersonenServiceException;
    void aendern(Person person) throws PersonenServiceException;
    void loeschen(UUID uuid) throws PersonenServiceException;
    Optional<Person> findeNachId(UUID uuid)throws PersonenServiceException ;
    Iterable<Person> findeAlle()throws PersonenServiceException;
}
