package solis3d.u5w3d5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "L'email inserita non è valida!")
        String email,

        @NotBlank(message = "La password è obbligatoria!")
        String password
) {
}



