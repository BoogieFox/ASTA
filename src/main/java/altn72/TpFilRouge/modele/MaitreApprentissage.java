package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "maitre_apprentissage", schema = "base_asta")
public class MaitreApprentissage {

    // Getters and Setters
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

}

