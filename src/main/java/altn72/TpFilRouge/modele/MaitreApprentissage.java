package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "maitre_apprentissage", schema = "base_asta")
public class MaitreApprentissage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maitre_apprentissage_id", nullable = false)
    private Integer maitreApprentissageId;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "telephone", nullable = false, length = 15)
    private String telephone;

    @Column(name = "poste", nullable = false, length = 100)
    private String poste;

    @Column(name = "remarques", length = 1024)
    private String remarques;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    @OneToMany(mappedBy = "maitreApprentissage")
    private List<Apprenti> apprentis;

    // Constructors
    public MaitreApprentissage() {
    }

    public MaitreApprentissage(String nom, String prenom, String email, String telephone, String poste) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.poste = poste;
    }

    // Getters and Setters
    public Integer getMaitreApprentissageId() {
        return maitreApprentissageId;
    }

    public void setMaitreApprentissageId(Integer maitreApprentissageId) {
        this.maitreApprentissageId = maitreApprentissageId;
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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public List<Apprenti> getApprentis() {
        return apprentis;
    }

    public void setApprentis(List<Apprenti> apprentis) {
        this.apprentis = apprentis;
    }
}

