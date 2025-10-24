package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "soutenance", schema = "base_asta")
public class Soutenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soutenance_id", nullable = false)
    private Integer soutenanceId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note_finale")
    private Double noteFinale;

    @Column(name = "commentaires", length = 1024)
    private String commentaires;

    @OneToOne
    @JoinColumn(name = "dossier_annuel_id", nullable = false)
    private DossierAnnuel dossierAnnuel;

    // Constructors
    public Soutenance() {
    }

    public Soutenance(LocalDate date, DossierAnnuel dossierAnnuel) {
        this.date = date;
        this.dossierAnnuel = dossierAnnuel;
    }

    // Getters and Setters
    public Integer getSoutenanceId() {
        return soutenanceId;
    }

    public void setSoutenanceId(Integer soutenanceId) {
        this.soutenanceId = soutenanceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getNoteFinale() {
        return noteFinale;
    }

    public void setNoteFinale(Double noteFinale) {
        this.noteFinale = noteFinale;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public DossierAnnuel getDossierAnnuel() {
        return dossierAnnuel;
    }

    public void setDossierAnnuel(DossierAnnuel dossierAnnuel) {
        this.dossierAnnuel = dossierAnnuel;
    }
}
