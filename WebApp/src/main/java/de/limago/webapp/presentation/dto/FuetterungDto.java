package de.limago.webapp.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class FuetterungDto {


    @NotNull
    private UUID id;

    public FuetterungDto() {
    }

    public FuetterungDto(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FuetterungDto{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public void setId(final UUID id) {
        this.id = id;
    }
}
