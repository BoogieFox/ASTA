package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visite", schema = "base_asta")
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visite_id", nullable = false)
    private Integer visiteId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "format", length = 50)
    private String format;

    @Column(name = "commentaires", length = 1024)
    private String commentaires;

    @OneToOne
    @JoinColumn(name = "dossier_annuel_id", nullable = false)
    private DossierAnnuel dossierAnnuel;

    // Constructors
    public Visite() {
    }

    public Visite(LocalDate date, String format, DossierAnnuel dossierAnnuel) {
        this.date = date;
        this.format = format;
        this.dossierAnnuel = dossierAnnuel;
    }

    // Getters and Setters
    public Integer getVisiteId() {
        return visiteId;
    }

    public void setVisiteId(Integer visiteId) {
        this.visiteId = visiteId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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
