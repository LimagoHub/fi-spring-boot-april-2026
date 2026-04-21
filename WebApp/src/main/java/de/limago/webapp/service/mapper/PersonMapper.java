package de.limago.webapp.service.mapper;

import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person convert(PersonEntity personEntity);
    PersonEntity convert(Person person);
    Iterable<Person> convert(Iterable<PersonEntity> personEntity);
}
