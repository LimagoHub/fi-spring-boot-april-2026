package de.limago.webapp.presentation.mapper;

import de.limago.webapp.presentation.dto.SchweinDto;
import de.limago.webapp.service.model.Schwein;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinDtoMapper {

    SchweinDto convert(final Schwein schwein);
    Schwein convert(final SchweinDto dto);

    Iterable<SchweinDto> convert(final Iterable<Schwein> schweine);
}
