package de.limago.webapp.presentation.controller;


import de.limago.webapp.presentation.dto.PersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/personen")
public class PersonenController {



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
    public ResponseEntity<PersonDto> findById(@PathVariable UUID id) {

        Optional<PersonDto> optional;

        if(id.toString().endsWith("07"))
            optional = Optional.empty();
        else
            optional = Optional.of(new PersonDto( id, "Max", "Mustermann"));


        return ResponseEntity.of(optional);
    }

    @GetMapping(path="", produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<PersonDto>> findAll(
            @RequestParam(required = false, defaultValue = "Max") String vorname,
            @RequestParam(required = false, defaultValue = "Mustermann") String nachname
    ) {

        System.out.println(vorname + " " + nachname);

        var liste = List.of(
                new PersonDto( UUID.randomUUID(), "Max", "Mustermann"),
                new PersonDto( UUID.randomUUID(), "Erika", "Mustermann"),
            new PersonDto( UUID.randomUUID(), "John", "Doe")
        );
               return ResponseEntity.ok(liste);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        System.out.println("delete " + id);
        if(id.toString().endsWith("07"))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
    @PostMapping(path="",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@RequestBody PersonDto personDto, UriComponentsBuilder uriBuilder ) {
        UriComponents uriComponents = uriBuilder.path("/v1/personen/{id}").buildAndExpand(personDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody PersonDto personDto) {
        if(!id.equals(personDto.getId())) throw new IllegalArgumentException("Upps");
        if(id.toString().endsWith("07"))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
