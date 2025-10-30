package altn72.TpFilRouge.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "annee_academique_courante", schema = "base_asta")
public class AnneeAcademiqueCourante {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "valeur", nullable = false, length = 9)
    private String valeur;

    public AnneeAcademiqueCourante() {
    }

    public AnneeAcademiqueCourante(Long id, String valeur) {
        this.id = id;
        this.valeur = valeur;
    }

}
