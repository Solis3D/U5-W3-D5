package solis3d.u5w3d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import solis3d.u5w3d5.entities.Utente;

import java.util.Optional;
import java.util.UUID;

public interface UtentiRepository extends JpaRepository<Utente, UUID> {
    boolean existsByEmail(String email);

    Optional<Utente> findByEmail(String email);
}
