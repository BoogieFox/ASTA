package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // Relation bidirectionnelle OneToOne avec Mission
    @OneToOne(mappedBy = "apprenti", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Mission mission;


    // Constructors
    public Apprenti() {
        // Pas besoin d'initialiser mission car c'est une relation OneToOne
    }

    public Apprenti(String nom, String prenom, String email, String telephone, String majeure) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.majeure = majeure;
        this.promotion = Promotion.L1; // Par défaut en L1
        this.dossierAnnuels = new ArrayList<>();
    }

    // Getters and Setters
    public Integer getApprentiId() {
        return apprentiId;
    }

    public void setApprentiId(Integer apprentiId) {
        this.apprentiId = apprentiId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMajeure() {
        return majeure;
    }

    public void setMajeure(String majeure) {
        this.majeure = majeure;
    }

    public List<DossierAnnuel> getDossierAnnuels() {
        return dossierAnnuels;
    }

    public void setDossierAnnuels(List<DossierAnnuel> dossierAnnuels) {
        this.dossierAnnuels = dossierAnnuels;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public MaitreApprentissage getMaitreApprentissage() {
        return maitreApprentissage;
    }

    public void setMaitreApprentissage(MaitreApprentissage maitreApprentissage) {
        this.maitreApprentissage = maitreApprentissage;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void ajouterDossierAnnuel(DossierAnnuel dossierAnnuel) {
        dossierAnnuel.setApprenti(this);
        this.dossierAnnuels.add(dossierAnnuel);
    }
}