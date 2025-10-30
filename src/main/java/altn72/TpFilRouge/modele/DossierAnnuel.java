package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(
    name = "dossier_annuel",
    schema = "base_asta",
    uniqueConstraints = @UniqueConstraint(name = "uk_dossier_apprenti_promotion", columnNames = {"apprenti_id", "promotion"})
)
public class DossierAnnuel {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dossier_annuel_id", nullable = false)
    private Integer dossierAnnuelId;

    @ManyToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    private Apprenti apprenti;

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion", nullable = false)
    private Promotion promotion;

    @OneToOne(mappedBy = "dossierAnnuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Rapport rapport;

    @OneToOne(mappedBy = "dossierAnnuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Visite visite;

    @OneToOne(mappedBy = "dossierAnnuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Soutenance soutenance;

    public DossierAnnuel() {
    }

    public DossierAnnuel(Apprenti apprenti, Promotion promotion) {
        this.apprenti = apprenti;
        this.promotion = promotion;
    }

}
