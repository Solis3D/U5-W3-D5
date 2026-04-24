package solis3d.u5w3d5.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "prenotazioni",
        uniqueConstraints = @UniqueConstraint(columnNames = {"utente_id", "evento_id"})
)
public class Prenotazione {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "data_prenotazione", nullable = false)
    private LocalDateTime dataPrenotazione;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    public Prenotazione() {
    }

    public Prenotazione(LocalDateTime dataPrenotazione, Utente utente, Evento evento) {
        this.dataPrenotazione = dataPrenotazione;
        this.utente = utente;
        this.evento = evento;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}