package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "rapport", schema = "base_asta")
public class Rapport {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rapport_id", nullable = false)
    private Integer rapportId;

    @Column(name = "sujet")
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

}
