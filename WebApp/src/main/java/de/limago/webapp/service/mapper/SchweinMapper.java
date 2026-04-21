package de.limago.webapp.service.mapper;

import de.limago.webapp.persistence.entity.SchweinEntity;
import de.limago.webapp.service.model.Schwein;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinMapper {

    Schwein convert(SchweinEntity schwein);
    SchweinEntity convert(Schwein schwein);
    Iterable<Schwein> convert(Iterable<SchweinEntity> schweine);
}
