package de.limago.webapp.presentation.mapper;

import de.limago.webapp.persistence.entity.PersonEntity;
import de.limago.webapp.presentation.dto.PersonDto;
import de.limago.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDtoMapper {

    PersonDto convert(Person person);
    Person convert(PersonDto personDto);
    Iterable<PersonDto> convert(Iterable<Person> persons);
}
