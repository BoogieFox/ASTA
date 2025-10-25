package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "entreprise", schema = "base_asta")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entreprise_id", nullable = false)
    private Integer entrepriseId;

    @Column(name = "raison_sociale", nullable = false, length = 100, unique = true)
    private String raisonSociale;

    @Column(name = "adresse", nullable = false, length = 255)
    private String adresse;

    @Column(name = "informations_locaux", length = 1024)
    private String informationsLocaux;

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    private List<Apprenti> apprentis;

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    private List<MaitreApprentissage> maitresApprentissage;

    public Entreprise() {
    }

    public Entreprise(String raisonSociale, String adresse) {
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
    }

    // Getters and Setters
    public Integer getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Integer entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInformationsLocaux() {
        return informationsLocaux;
    }

    public void setInformationsLocaux(String informationsLocaux) {
        this.informationsLocaux = informationsLocaux;
    }

    public List<Apprenti> getApprentis() {
        return apprentis;
    }

    public void setApprentis(List<Apprenti> apprentis) {
        this.apprentis = apprentis;
    }

    public List<MaitreApprentissage> getMaitresApprentissage() {
        return maitresApprentissage;
    }

    public void setMaitresApprentissage(List<MaitreApprentissage> maitresApprentissage) {
        this.maitresApprentissage = maitresApprentissage;
    }
}