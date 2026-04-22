package de.limago.webapp.service.config;


import de.limago.webapp.persistence.repository.PersonRepository;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.internal.PersonenServiceImpl;
import de.limago.webapp.service.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PersonConfig {

    @Bean
    @Qualifier("antipathen")
    public List<String> createAntipathen() {
        return List.of("Attila", "Peter","Paul","Mary");
    }


    @Bean
    @Qualifier("fruits")
    public List<String> createFruits() {
        return List.of("Banana", "Cherry","Strawberry","Raspberry");
    }


    @Bean
    public PersonenService createPersonenService(final PersonRepository repo, final PersonMapper mapper, @Qualifier("antipathen") final List<String> antipathen) {
        return new PersonenServiceImpl(repo, mapper, antipathen);
    }
}
