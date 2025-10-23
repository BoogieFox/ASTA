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

    @ManyToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    private Apprenti apprenti;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id", nullable = false)
    private AnneeAcademique anneeAcademique;

    // Constructors
    public Soutenance() {
    }

    public Soutenance(LocalDate date, Apprenti apprenti, AnneeAcademique anneeAcademique) {
        this.date = date;
        this.apprenti = apprenti;
        this.anneeAcademique = anneeAcademique;
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

    public Apprenti getApprenti() {
        return apprenti;
    }

    public void setApprenti(Apprenti apprenti) {
        this.apprenti = apprenti;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }
}
