package solis3d.u5w3d5.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EventoDTO(
        @NotBlank(message = "Il titolo è obbligatorio!")
        @Size(min = 2, max = 100, message = "Il titolo deve essere compreso tra 2 e 100 caratteri!")
        String titolo,

        @Size(max = 1000, message = "La descrizione non può superare i 1000 caratteri")
        String descrizione,

        @NotNull(message = "La data è obbligatoria")
        @FutureOrPresent(message = "La data dell'evento non può essere nel passato!")
        LocalDate data,

        @NotBlank(message = "Il luogo è obbligatorio")
        @Size(min = 2, max = 100, message = "Il luogo deve essere compreso tra 2 e 100 caratteri!")
        String luogo,

        @Positive(message = "I posti disponibili devono essere maggiori di 0!")
        int postiDisponibili
) {
}
