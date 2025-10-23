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

    @ManyToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    private Apprenti apprenti;

    // Constructors
    public Rapport() {
    }

    public Rapport(String sujet, Apprenti apprenti) {
        this.sujet = sujet;
        this.apprenti = apprenti;
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

    public Apprenti getApprenti() {
        return apprenti;
    }

    public void setApprenti(Apprenti apprenti) {
        this.apprenti = apprenti;
    }
}
