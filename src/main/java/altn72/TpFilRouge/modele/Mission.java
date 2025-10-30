package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "mission", schema = "base_asta")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id", nullable = false)
    private Integer missionId;

    @Column(name = "mots_cles")
    private String motsCles;

    @Column(name = "metier_cible", length = 100)
    private String metierCible;

    @Column(name = "commentaires", length = 1024)
    private String commentaires;

    @OneToOne
    @JoinColumn(name = "apprenti_id", nullable = false, unique = true)
    private Apprenti apprenti;

    public Mission() {
    }

    public Mission(String motsCles, String metierCible, Apprenti apprenti) {
        this.motsCles = motsCles;
        this.metierCible = metierCible;
        this.apprenti = apprenti;
    }

}
