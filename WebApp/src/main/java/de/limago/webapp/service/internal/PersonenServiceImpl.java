package de.limago.webapp.service.internal;

import de.limago.webapp.persistence.repository.PersonRepository;
import de.limago.webapp.service.BlacklistService;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.event.PersonCreatedEvent;
import de.limago.webapp.service.exception.AlreadyExistsException;
import de.limago.webapp.service.exception.BlacklistedPersonException;
import de.limago.webapp.service.exception.NotFoundException;
import de.limago.webapp.service.exception.PersonenServiceException;
import de.limago.webapp.service.mapper.PersonMapper;
import de.limago.webapp.service.model.Person;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor = PersonenServiceException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class PersonenServiceImpl implements PersonenService {

    private final PersonRepository personRepository;
    private final PersonMapper mapper;
    private final List<String> antipathen;
    private ApplicationEventPublisher applicationEventPublisher;

    public PersonenServiceImpl(final List<String> antipathen, final ApplicationEventPublisher applicationEventPublisher, final PersonMapper mapper, final PersonRepository personRepository) {
        this.antipathen = antipathen;
        this.applicationEventPublisher = applicationEventPublisher;
        this.mapper = mapper;
        this.personRepository = personRepository;
    }

    @Override
    public void speichern(final Person person) throws PersonenServiceException {
        validatePerson(person);
        if (personRepository.existsById(person.getId())) {
            throw new AlreadyExistsException("Person existiert bereits");
        }
        try {
            personRepository.save(mapper.convert(person));
            applicationEventPublisher.publishEvent(new PersonCreatedEvent(person.getId(), person.getVorname(), person.getNachname()));
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten", e);
        }
    }

    @Override
    public void aendern(final Person person) throws PersonenServiceException {
        validatePerson(person);
        if (!personRepository.existsById(person.getId())) {
            throw new NotFoundException("Person wurde nicht gefunden");
        }
        try {
            personRepository.save(mapper.convert(person));
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten", e);
        }
    }

    @Override
    public void loeschen(final UUID uuid) throws PersonenServiceException {
        if (!personRepository.existsById(uuid)) {
            throw new NotFoundException("Person wurde nicht gefunden");
        }
        try {
            personRepository.deleteById(uuid);
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Person> findeNachId(final UUID uuid) throws PersonenServiceException {
        try {
            return personRepository.findById(uuid).map(mapper::convert);
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return mapper.convert(personRepository.findAll());
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten", e);
        }
    }

    private void validatePerson(final Person person) throws PersonenServiceException {
        if (person == null) {
            throw new PersonenServiceException("Parameter darf nicht null sein");
        }
        if (person.getId() == null) {
            throw new PersonenServiceException("Person muss eine Id haben");
        }
        if (person.getVorname() == null || person.getVorname().length() < 2) {
            throw new PersonenServiceException("Vorname zu kurz");
        }
        if (person.getNachname() == null || person.getNachname().length() < 2) {
            throw new PersonenServiceException("Nachname zu kurz");
        }
        if(antipathen.contains(person.getVorname()))  {
            throw new BlacklistedPersonException("Unerwuenschte Person");
        }
    }
}
