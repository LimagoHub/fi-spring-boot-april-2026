package de.limago.webapp.service.internal;

import de.limago.webapp.persistence.repository.PersonRepository;
import de.limago.webapp.service.BlacklistService;
import de.limago.webapp.service.exception.PersonenServiceException;
import de.limago.webapp.service.mapper.PersonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {
    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonRepository personRepositoryMock;

    @Mock
    private PersonMapper mapperMock;

    @Mock
    private List<String> blacklistServiceMock;

    @Test
    @DisplayName("speichern mit leerem Parameter erwartet eine PersonenServiceException")
    void speichernParameterNull() throws Exception {
        final PersonenServiceException ex = assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichern(null));
        assertEquals("Parameter darf nicht null sein", ex.getMessage());
    }

}