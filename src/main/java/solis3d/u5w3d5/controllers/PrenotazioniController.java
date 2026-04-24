package solis3d.u5w3d5.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solis3d.u5w3d5.entities.Prenotazione;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.ValidationException;
import solis3d.u5w3d5.payloads.NewPrenotazioneRespDTO;
import solis3d.u5w3d5.payloads.PrenotazioneDTO;
import solis3d.u5w3d5.services.PrenotazioniService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    private final PrenotazioniService prenotazioniService;

    public PrenotazioniController(PrenotazioniService prenotazioniService) {
        this.prenotazioniService = prenotazioniService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewPrenotazioneRespDTO save(@RequestBody @Validated PrenotazioneDTO body,
                                       BindingResult validationResult,
                                       @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            throw new ValidationException(errors);
        }

        Prenotazione newPrenotazione = this.prenotazioniService.saveNewPrenotazione(body, currentAuthenticatedUser);
        return new NewPrenotazioneRespDTO(newPrenotazione.getId());
    }

    @GetMapping
    public List<Prenotazione> getMyPrenotazioni(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return this.prenotazioniService.findAllByUtente(currentAuthenticatedUser);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID prenotazioneId,
                                  @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.prenotazioniService.findByIdAndDelete(prenotazioneId, currentAuthenticatedUser);
    }
}
