package de.limago.webapp.service.internal;

import de.limago.webapp.persistence.repository.SchweinRepository;
import de.limago.webapp.service.SchweineService;
import de.limago.webapp.service.exception.AlreadyExistsException;
import de.limago.webapp.service.exception.NotFoundException;
import de.limago.webapp.service.exception.SchweineServiceException;
import de.limago.webapp.service.mapper.SchweinMapper;
import de.limago.webapp.service.model.Schwein;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SchweineServiceImpl implements SchweineService {

    private final SchweinRepository schweinRepository;
    private final SchweinMapper mapper;

    public SchweineServiceImpl(final SchweinRepository schweinRepository, final SchweinMapper mapper) {
        this.schweinRepository = schweinRepository;
        this.mapper = mapper;
    }

    @Override
    public void speichern(final Schwein schwein) {
        if(schweinRepository.existsById(schwein.getId())) throw new  AlreadyExistsException("Schwein gibt es schon");
        try {
            schweinRepository.save(mapper.convert(schwein));
        } catch (RuntimeException e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public void aendern(final Schwein schwein) {
        if(!schweinRepository.existsById(schwein.getId())) throw new NotFoundException("Schwein existiert nicht");
        try {
            schweinRepository.save(mapper.convert(schwein));
        } catch (RuntimeException e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public void loeschen(final UUID uuid) {
        if(!schweinRepository.existsById(uuid)) throw new NotFoundException("Schwein existiert nicht");
        try {
            schweinRepository.deleteById(uuid);
        } catch (RuntimeException e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public Optional<Schwein> findeNachId(final UUID uuid) {
        if(!schweinRepository.existsById(uuid)) throw new NotFoundException("Schwein existiert nicht");
        try {
            return schweinRepository.findById(uuid).map(mapper::convert);
        } catch (RuntimeException e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public Iterable<Schwein> findeAlle() {
        try {
            return mapper.convert(schweinRepository.findAll());
        } catch (RuntimeException e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public void fuettern(final UUID uuid) {
        Schwein schwein = schweinRepository.findById(uuid)
                .map(mapper::convert)
                .orElseThrow(() -> new NotFoundException("Schwein existiert nicht"));
        schwein.fuettern();
        try {
            schweinRepository.save(mapper.convert(schwein));
        } catch (RuntimeException e) {
            throw new SchweineServiceException("upps", e);
        }
    }
}
