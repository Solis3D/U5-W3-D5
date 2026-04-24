package solis3d.u5w3d5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import solis3d.u5w3d5.entities.Ruolo;

public record UtenteDTO(
        @NotBlank(message = "Il nome è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra 2 e 30 caratteri!")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra 2 e 30 caratteri!")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "L'email inserita non è valida!")
        String email,

        @NotBlank(message = "La password è obbligatoria!")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
        String password,

        @NotNull(message = "Il ruolo è obbligatorio!")
        Ruolo ruolo
) {
}
