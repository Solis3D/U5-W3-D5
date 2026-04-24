package solis3d.u5w3d5.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "eventi")
public class Evento {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Column(name = "posti_disponibili",nullable = false)
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id", nullable = false)
    private Utente organizzatore;

    public Evento() {}

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int postiDisponibili, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.postiDisponibili = postiDisponibili;
        this.organizzatore = organizzatore;
    }

    public UUID getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public Utente getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", luogo='" + luogo + '\'' +
                ", postiDisponibili=" + postiDisponibili +
                '}';
    }
}
