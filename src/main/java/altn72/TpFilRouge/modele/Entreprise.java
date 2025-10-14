package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "entreprise", schema = "base_asta")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entreprise_id", nullable = false)
    private Integer EntrepriseId;

    @Column(name = "raison_sociale", nullable = false, length = 100, unique = true)
    private String raisonSociale;

    @Column(name = "adresse", nullable = false, length = 128)
    private String adresse;

    @Column(name = "informations_locaux", nullable = false, length = 1024)
    private String informationsLocaux;

    @OneToMany(mappedBy = "entreprise")
    private List<Apprenti> apprentis;
}