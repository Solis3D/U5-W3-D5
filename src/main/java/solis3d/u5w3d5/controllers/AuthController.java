package solis3d.u5w3d5.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.ValidationException;
import solis3d.u5w3d5.payloads.LoginDTO;
import solis3d.u5w3d5.payloads.LoginRespDTO;
import solis3d.u5w3d5.payloads.NewUtenteRespDTO;
import solis3d.u5w3d5.payloads.UtenteDTO;
import solis3d.u5w3d5.services.AuthService;
import solis3d.u5w3d5.services.UtentiService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtentiService utentiService;

    public AuthController(AuthService authService, UtentiService utentiService) {
        this.authService = authService;
        this.utentiService = utentiService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

            throw new ValidationException(errors);
        }

        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteRespDTO register(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();

            throw new ValidationException(errors);
        }

        Utente newUtente = this.utentiService.saveNewUtente(body);
        return new NewUtenteRespDTO(newUtente.getId());
    }
}
