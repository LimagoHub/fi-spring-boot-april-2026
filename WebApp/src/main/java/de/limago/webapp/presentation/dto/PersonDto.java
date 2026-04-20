package de.limago.webapp.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class PersonDto {

    @NotNull
    private UUID id;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String vorname;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String nachname;

    public PersonDto() {
    }

    public PersonDto(final UUID id ,final String vorname, final String nachname) {
        this.nachname = nachname;
        this.id = id;
        this.vorname = vorname;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonDto{");
        sb.append("id=").append(id);
        sb.append(", vorname='").append(vorname).append('\'');
        sb.append(", nachname='").append(nachname).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(final String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(final String nachname) {
        this.nachname = nachname;
    }
}
