package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "apprenti", schema = "base_asta")
public class Apprenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apprenti_id", nullable = false)
    private Integer apprentiId;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "telephone", nullable = false, length = 15)
    private String telephone;

    @Column(name = "majeure", nullable = false, length = 100)
    private String majeure;

    @Column(name = "annee_academique", length = 9)
    private String anneeAcademique;

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion", nullable = false)
    private Promotion promotion = Promotion.L1;

    @OneToMany(mappedBy = "apprenti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DossierAnnuel> dossierAnnuels = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "maitre_apprentissage_id")
    private MaitreApprentissage maitreApprentissage;


    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private Tuteur tuteur;

    // Relation bidirectionnelle OneToOne avec Mission
    @OneToOne(mappedBy = "apprenti", cascade = CascadeType.ALL, orphanRemoval = true)
    private Mission mission;


    // Constructors
    public Apprenti() {
        // Pas besoin d'initialiser mission, car c'est une relation OneToOne
    }

    public Apprenti(String nom, String prenom, String email, String telephone, String majeure) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.majeure = majeure;
        this.dossierAnnuels = new ArrayList<>();
    }

    public void ajouterDossierAnnuel(DossierAnnuel dossierAnnuel) {
        dossierAnnuel.setApprenti(this);
        this.dossierAnnuels.add(dossierAnnuel);
    }
}
