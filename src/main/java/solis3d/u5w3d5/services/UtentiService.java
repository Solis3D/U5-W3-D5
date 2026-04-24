package solis3d.u5w3d5.services;

import org.springframework.stereotype.Service;
import solis3d.u5w3d5.entities.Utente;
import solis3d.u5w3d5.exceptions.BadRequestException;
import solis3d.u5w3d5.exceptions.NotFoundException;
import solis3d.u5w3d5.payloads.UtenteDTO;
import solis3d.u5w3d5.repositories.UtentiRepository;

import java.util.UUID;

@Service
public class UtentiService {

    private final UtentiRepository utentiRepository;

    public UtentiService(UtentiRepository utentiRepository) {
        this.utentiRepository = utentiRepository;
    }

    public Utente saveNewUtente(UtenteDTO body) {
        if (this.utentiRepository.existsByEmail(body.email())){
            throw new BadRequestException("L'email è già in uso!");
        }

        Utente newUtente = new Utente(
                body.nome(),
                body.cognome(),
                body.email(),
                body.password(),
                body.ruolo()
        );

        return utentiRepository.save(newUtente);
    }

    public Utente findById(UUID utenteId){
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByEmail(String email){
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato!" ));
    }
}
