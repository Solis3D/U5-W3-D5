package solis3d.u5w3d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import solis3d.u5w3d5.entities.Prenotazione;

import java.util.List;
import java.util.UUID;

public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    boolean existsByUtenteIdAndEventoId(UUID utenteId, UUID eventoId);

    List<Prenotazione> findByUtenteId(UUID utenteId);
}
