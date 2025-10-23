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

    @ManyToOne
    @JoinColumn(name = "annee_academique_id")
    private AnneeAcademique anneeAcademique;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "maitre_apprentissage_id")
    private MaitreApprentissage maitreApprentissage;

    // Relations bidirectionnelles avec cascade pour permettre l'ajout via l'apprenti
    @OneToMany(mappedBy = "apprenti", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mission> missions;

    @OneToMany(mappedBy = "apprenti", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visite> visites;

    @OneToMany(mappedBy = "apprenti", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rapport> rapports;

    @OneToMany(mappedBy = "apprenti", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soutenance> soutenances;

    // Constructors
    public Apprenti() {
        this.missions = new ArrayList<>();
        this.visites = new ArrayList<>();
        this.rapports = new ArrayList<>();
        this.soutenances = new ArrayList<>();
    }

    public Apprenti(String nom, String prenom, String email, String telephone, String majeure) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.majeure = majeure;
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

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
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

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public List<Visite> getVisites() {
        return visites;
    }

    public void setVisites(List<Visite> visites) {
        this.visites = visites;
    }

    public List<Rapport> getRapports() {
        return rapports;
    }

    public void setRapports(List<Rapport> rapports) {
        this.rapports = rapports;
    }

    public List<Soutenance> getSoutenances() {
        return soutenances;
    }

    public void setSoutenances(List<Soutenance> soutenances) {
        this.soutenances = soutenances;
    }
}