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

    @Column(name = "poste", nullable = false, length = 100)
    private String poste;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "remarques", nullable = false, length = 100)
    private String remarques;

    @Column(name = "telephone", nullable = false, length = 10, unique = true)
    private String telephone;

    @OneToMany(mappedBy = "maitre_apprentissage")
    private List<Apprenti> apprentis;
}

