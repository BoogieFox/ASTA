package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "soutenance", schema = "base_asta")
public class Soutenance {
    // Getters and Setters
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

}
