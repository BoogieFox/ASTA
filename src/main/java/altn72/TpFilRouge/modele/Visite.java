package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
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
}
