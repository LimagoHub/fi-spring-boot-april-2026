package de.limago.webapp.demo;


import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.persistence.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Demo {


    private final String message;

    public Demo(@Value("${Demo.message}") String message) {
        this.message = message;
        System.out.println(message);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(message);
    }
}
