package solis3d.u5w3d5.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import solis3d.u5w3d5.entities.Evento;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.ValidationException;
import solis3d.u5w3d5.payloads.EventoDTO;
import solis3d.u5w3d5.payloads.NewEventoRespDTO;
import solis3d.u5w3d5.services.EventiService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    private final EventiService eventiService;

    public EventiController(EventiService eventiService) {
        this.eventiService = eventiService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventoRespDTO saveEvento(@RequestBody @Validated EventoDTO body,
                                       BindingResult validationResult,
                                       @AuthenticationPrincipal Utente currentAuthenticatedUser) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            throw new ValidationException(errors);
        }

        Evento newEvento = this.eventiService.saveNewEvento(body, currentAuthenticatedUser);
        return new NewEventoRespDTO(newEvento.getId());
    }

    @GetMapping
    public List<Evento> getEventi() {
        return this.eventiService.findAll();
    }

    @GetMapping("/{eventoId}")
    public Evento getEventoById(@PathVariable UUID eventoId) {
        return this.eventiService.findById(eventoId);
    }

    @PutMapping("/{eventoId}")
    public Evento findByIdAndUpdate(@PathVariable UUID eventoId,
                                    @RequestBody @Validated EventoDTO body,
                                    BindingResult validationResult,
                                    @AuthenticationPrincipal Utente currentAuthenticatedUser) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            throw new ValidationException(errors);
        }

        return this.eventiService.findByIdAndUpdate(eventoId, body, currentAuthenticatedUser);
    }

    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID eventoId,
                                  @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.eventiService.findByIdAndDelete(eventoId, currentAuthenticatedUser);
    }
}
