package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "entreprise", schema = "base_asta")
public class Entreprise {
    // Getters and Setters
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

}