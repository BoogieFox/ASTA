package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

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

    @Column(name = "telephone", nullable = false, length = 10, unique = true)
    private String telephone;

    @Column(name = "majeure", nullable = false, length = 100)
    private String majeure;

    @Column(name = "annee_academique", nullable = false, length = 100)
    private String anneeAcademique;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "maitre_apprentissage_id")
    private Entreprise maitreApprentissage;
}