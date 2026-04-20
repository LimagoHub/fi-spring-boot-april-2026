package de.limago.webapp.demo;


import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.persistence.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Demo {

    private final PersonRepository personRepository;

    public Demo(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void postConstruct() {
        //PersonEntity person = new PersonEntity("John Boy", UUID.randomUUID(), "Walton");
        //personRepository.save(person);

        personRepository.findByNachname("John").forEach(System.out::println);
    }
}
