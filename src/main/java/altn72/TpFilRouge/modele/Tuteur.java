package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tuteur", schema = "base_asta")
public class Tuteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tuteur_id", nullable = false)
    private Integer tuteurId;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @OneToMany(mappedBy = "tuteur")
    private List<Apprenti> apprentis;

}
