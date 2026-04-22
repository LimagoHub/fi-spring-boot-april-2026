package de.limago.webapp.demo;


import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.persistence.repository.PersonRepository;
import de.limago.webapp.service.MailServiceDummy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Demo {


    private final MailServiceDummy mailService;



    public Demo(final MailServiceDummy mailService) {
        this.mailService = mailService;
    }

    @PostConstruct
    public void postConstruct() {
        mailService.send("Foo", "Bar");
    }
}
