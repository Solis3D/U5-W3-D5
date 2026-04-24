package solis3d.u5w3d5.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrenotazioneDTO(
        @NotNull(message = "L'id dell'evento è obbligatorio!")
        UUID eventoId
) {
}
