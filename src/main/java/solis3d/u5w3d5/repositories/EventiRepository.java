package solis3d.u5w3d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import solis3d.u5w3d5.entities.Evento;

import java.util.UUID;

public interface EventiRepository extends JpaRepository<Evento, UUID> {
}
