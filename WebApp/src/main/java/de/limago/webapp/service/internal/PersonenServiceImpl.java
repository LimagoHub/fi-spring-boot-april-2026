package de.limago.webapp.service.internal;

import de.limago.webapp.persistence.repository.PersonRepository;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.exception.AlreadyExistsException;
import de.limago.webapp.service.exception.NotFoundException;
import de.limago.webapp.service.exception.PersonenServiceException;
import de.limago.webapp.service.mapper.PersonMapper;
import de.limago.webapp.service.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor=PersonenServiceException.class, propagation = Propagation.REQUIRED)
public class PersonenServiceImpl implements PersonenService {

    private final PersonRepository personRepository;
    private final PersonMapper mapper;

    public PersonenServiceImpl(final PersonRepository personRepository, final PersonMapper mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    /*
                person == null -> PSE
                vorname == null oder zu kurz ->PSE
                nachname dito

                Attila -> PSE

                exception in underlying service ->PSE

                happy day -> Person to Repo

             */

    @Override
    public void speichern(Person person) throws PersonenServiceException {
        try {
            if(person == null) {
                throw new PersonenServiceException("Parameter darf nicht null sein");
            }
            if(person.getId() == null) {
                throw new PersonenServiceException("Person muss eine Id haben");
            }
            if(personRepository.existsById(person.getId())) {
                throw new AlreadyExistsException("Person existiert bereits");
            }
            if(person.getVorname() == null || person.getVorname().length() < 2)
                throw new PersonenServiceException("Vorname zu kurz");

            if(person.getNachname() == null || person.getNachname().length() < 2)
                throw new PersonenServiceException("Nachname zu kurz");

            if(person.getVorname().equals("Attila"))
                throw new PersonenServiceException("Unerwuenschte Person");

            personRepository.save(mapper.convert(person));

        } catch (AlreadyExistsException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten",e);
        }

    }

    @Override
    public void aendern(final Person person) throws PersonenServiceException {
        try {
            if(person == null) {
                throw new PersonenServiceException("Parameter darf nicht null sein");
            }
            if(person.getId() == null) {
                throw new PersonenServiceException("Person muss eine Id haben");
            }
            if(! personRepository.existsById(person.getId())) {
                throw new NotFoundException("Person wurde nicht gefunden");
            }
            if(person.getVorname() == null || person.getVorname().length() < 2)
                throw new PersonenServiceException("Vorname zu kurz");

            if(person.getNachname() == null || person.getNachname().length() < 2)
                throw new PersonenServiceException("Nachname zu kurz");

            if(person.getVorname().equals("Attila"))
                throw new PersonenServiceException("Unerwuenschte Person");

            personRepository.save(mapper.convert(person));
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Ein Fehler ist aufgetreten",e);
        }
    }

    @Override
    public void loeschen(final UUID uuid) throws PersonenServiceException {
        try {
            if(! personRepository.existsById(uuid)) throw new NotFoundException("Person wurde nicht gefunden");
            personRepository.deleteById(uuid);

        }  catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Upps",e);
        }
    }

    @Override
    public Optional<Person> findeNachId(final UUID uuid) throws PersonenServiceException {
        try {

            return personRepository.findById(uuid).map(mapper::convert);
        } catch (Exception e) {
            throw new PersonenServiceException("Upps",e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return mapper.convert(personRepository.findAll());
        } catch (Exception e) {
            throw new PersonenServiceException("Upps", e);
        }
    }
}
