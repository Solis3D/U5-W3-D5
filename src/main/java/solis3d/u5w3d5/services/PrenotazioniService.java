package solis3d.u5w3d5.services;

import org.springframework.stereotype.Service;
import solis3d.u5w3d5.entities.Evento;
import solis3d.u5w3d5.entities.Prenotazione;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.BadRequestException;
import solis3d.u5w3d5.exceptions.NotFoundException;
import solis3d.u5w3d5.exceptions.UnauthorizedException;
import solis3d.u5w3d5.payloads.PrenotazioneDTO;
import solis3d.u5w3d5.repositories.PrenotazioniRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {

    private final PrenotazioniRepository prenotazioniRepository;
    private final EventiService eventiService;

    public PrenotazioniService(PrenotazioniRepository prenotazioniRepository, EventiService eventiService) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.eventiService = eventiService;
    }

    public Prenotazione saveNewPrenotazione(PrenotazioneDTO body, Utente currentAuthenticatedUser) {
        Evento trovato = this.eventiService.findById(body.eventoId());

        if(this.prenotazioniRepository.existsByUtenteIdAndEventoId(currentAuthenticatedUser.getId(), trovato.getId())){
            throw new BadRequestException("Hai già prenotato questo evento!");
        }

        if (trovato.getPostiDisponibili() <= 0) {
            throw new BadRequestException("Non ci sono più posti disponibili per questo evento!");
        }

        Prenotazione newPrenotazione = new Prenotazione(
                LocalDateTime.now(),
                currentAuthenticatedUser,
                trovato
        );

        trovato.setPostiDisponibili(trovato.getPostiDisponibili() - 1);

        return this.prenotazioniRepository.save(newPrenotazione);
    }

    public List<Prenotazione> findAllByUtente(Utente currentAuthenticatedUser) {
        return this.prenotazioniRepository.findByUtenteId(currentAuthenticatedUser.getId());
    }

    public Prenotazione findById(UUID prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public void findByIdAndDelete(UUID prenotazioneId, Utente currentAuthenticatedUser) {
        Prenotazione trovata = this.findById(prenotazioneId);

        if(!trovata.getUtente().getId().equals(currentAuthenticatedUser.getId())) {
            throw new UnauthorizedException("Puoi annullare solo le prenotazione effettuate da te!");
        }

        Evento evento = trovata.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);

        this.prenotazioniRepository.delete(trovata);
    }
}
