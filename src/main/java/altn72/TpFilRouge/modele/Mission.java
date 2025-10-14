package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id", nullable = false)
    private Integer missionId;

    @Column(name = "mots_cles", nullable = false, length = 100)
    private String motsCles;

    @Column(name = "metier_cible", nullable = false, length = 100)
    private String metierCible;

    @Column(name = "commentaires", nullable = false, length = 100)
    private String Commentaires;

    @OneToOne
    @JoinColumn(name = "apprenti_id")
    private Apprenti apprenti;
}
