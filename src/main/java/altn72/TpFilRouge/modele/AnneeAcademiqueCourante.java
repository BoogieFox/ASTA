package altn72.TpFilRouge.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
