package de.limago.webapp.presentation.controller;

import de.limago.webapp.presentation.dto.FuetterungDto;
import de.limago.webapp.presentation.dto.PersonDto;
import de.limago.webapp.presentation.dto.SchweinDto;
import de.limago.webapp.presentation.exception.IdMismatchException;
import de.limago.webapp.presentation.mapper.SchweinDtoMapper;
import de.limago.webapp.service.SchweineService;
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

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/v1/schweine")
public class SchweinController {

    private final SchweineService service;
    private final SchweinDtoMapper mapper;


    public SchweinController(final SchweineService service, final SchweinDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Liefert ein Schwein")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schwein gefunden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SchweinDto.class)) }),
            @ApiResponse(responseCode = "400", description = "ungueltige ID",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Schwein nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SchweinDto> findById(@PathVariable UUID id) {

        return ResponseEntity.of(service.findeNachId(id).map(mapper::convert));
    }

    @GetMapping(path="", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<SchweinDto>> findAll()  {


        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id)  {
        service.loeschen(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping(path="",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@Valid @RequestBody SchweinDto schweinDto, UriComponentsBuilder uriBuilder )  throws PersonenServiceException{
        service.speichern(mapper.convert(schweinDto));
        UriComponents uriComponents = uriBuilder.path("/v1/schweine/{id}").buildAndExpand(schweinDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable UUID id,@Valid @RequestBody SchweinDto schweinDto) throws PersonenServiceException {
        if (! id.equals(schweinDto.getId())) throw new IdMismatchException("ID mismatch");
        service.aendern(mapper.convert(schweinDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{id}/fuetterungen", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> fuettern(@PathVariable UUID id,@Valid @RequestBody FuetterungDto fuetterungDto  )   {
        service.fuettern(id, fuetterungDto.getId());
        return ResponseEntity.ok().build();
    }
}
