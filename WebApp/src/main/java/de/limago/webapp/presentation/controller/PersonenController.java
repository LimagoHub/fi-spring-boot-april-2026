package de.limago.webapp.presentation.controller;


import de.limago.webapp.presentation.exception.IdMismatchException;
import de.limago.webapp.presentation.dto.PersonDto;
import de.limago.webapp.presentation.mapper.PersonDtoMapper;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.exception.PersonenServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/v1/personen")
public class PersonenController {

    private final PersonenService personenService;
    private final PersonDtoMapper mapper;

    public PersonenController(final PersonenService personenService, final PersonDtoMapper mapper) {
        this.personenService = personenService;
        this.mapper = mapper;
    }

    @Operation(summary = "Liefert eine Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person gefunden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonDto.class)) }),
            @ApiResponse(responseCode = "400", description = "ungueltige ID",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Person nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PersonDto> findById(@PathVariable UUID id) throws PersonenServiceException {

        return ResponseEntity.of(personenService.findeNachId(id).map(mapper::convert));
    }

    @GetMapping(path="", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<PersonDto>> findAll(
            @RequestParam(required = false, defaultValue = "Max") String vorname,
            @RequestParam(required = false, defaultValue = "Mustermann") String nachname
    )  throws PersonenServiceException{

        System.out.println(vorname + " " + nachname);
        return ResponseEntity.ok(mapper.convert(personenService.findeAlle()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws PersonenServiceException {
        personenService.loeschen(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping(path="",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@Valid @RequestBody PersonDto personDto, UriComponentsBuilder uriBuilder )  throws PersonenServiceException{
        personenService.speichern(mapper.convert(personDto));
        UriComponents uriComponents = uriBuilder.path("/v1/personen/{id}").buildAndExpand(personDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable UUID id,@Valid @RequestBody PersonDto personDto) throws PersonenServiceException {
        if (! id.equals(personDto.getId())) throw new IdMismatchException("ID mismatch");
        personenService.aendern(mapper.convert(personDto));
        return ResponseEntity.ok().build();
    }
}
