package solis3d.u5w3d5.services;

import org.springframework.stereotype.Service;
import solis3d.u5w3d5.entities.Evento;
import solis3d.u5w3d5.entities.Ruolo;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.NotFoundException;
import solis3d.u5w3d5.exceptions.UnauthorizedException;
import solis3d.u5w3d5.payloads.EventoDTO;
import solis3d.u5w3d5.repositories.EventiRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EventiService {

    private final EventiRepository eventiRepository;

    public EventiService(EventiRepository eventiRepository) {
        this.eventiRepository = eventiRepository;
    }

    public Evento saveNewEvento(EventoDTO body, Utente organizzatore) {
        if(organizzatore.getRuolo() != Ruolo.ORGANIZZATORE_EVENTI) {
            throw new UnauthorizedException("Solo gli organizzatori possono creare nuovi eventi!");
        }

        Evento newEvento = new Evento(
                body.titolo(),
                body.descrizione(),
                body.data(),
                body.luogo(),
                body.postiDisponibili(),
                organizzatore
        );

        return this.eventiRepository.save(newEvento);
    }

    public List<Evento> findAll() {
        return this.eventiRepository.findAll();
    }

    public Evento findById(UUID eventoId) {
        return this.eventiRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException(eventoId));
    }

    public Evento findByIdAndUpdate(UUID eventoId, EventoDTO body, Utente currentUser) {
        Evento found = this.findById(eventoId);

        if (currentUser.getRuolo() != Ruolo.ORGANIZZATORE_EVENTI) {
            throw new UnauthorizedException("Solo gli organizzatori possono modificare eventi!");
        }

        if (!found.getOrganizzatore().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Puoi modificare solo gli eventi che hai creato!");
        }

        found.setTitolo(body.titolo());
        found.setDescrizione(body.descrizione());
        found.setData(body.data());
        found.setLuogo(body.luogo());
        found.setPostiDisponibili(body.postiDisponibili());

        return this.eventiRepository.save(found);
    }

    public void findByIdAndDelete(UUID eventoId, Utente currentUser) {
        Evento found = this.findById(eventoId);

        if (currentUser.getRuolo() != Ruolo.ORGANIZZATORE_EVENTI) {
            throw new UnauthorizedException("Solo gli organizzatori possono eliminare eventi!");
        }

        if (!found.getOrganizzatore().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Puoi eliminare solo gli eventi che hai creato!");
        }

        this.eventiRepository.delete(found);
    }
}
