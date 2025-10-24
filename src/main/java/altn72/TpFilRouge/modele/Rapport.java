package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "rapport", schema = "base_asta")
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rapport_id", nullable = false)
    private Integer rapportId;

    @Column(name = "sujet", length = 255)
    private String sujet;

    @Column(name = "note_finale")
    private Double noteFinale;

    @Column(name = "commentaires", length = 1024)
    private String commentaires;

    @OneToOne
    @JoinColumn(name = "dossier_annuel_id", nullable = false)
    private DossierAnnuel dossierAnnuel;

    // Constructors
    public Rapport() {
    }

    public Rapport(String sujet, DossierAnnuel dossierAnnuel) {
        this.sujet = sujet;
        this.dossierAnnuel = dossierAnnuel;
    }

    // Getters and Setters
    public Integer getRapportId() {
        return rapportId;
    }

    public void setRapportId(Integer rapportId) {
        this.rapportId = rapportId;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
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
