package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "annee_academique", schema = "base_asta")
public class AnneeAcademique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annee_academique_id", nullable = false)
    private Integer anneeAcademiqueId;

    @Column(name = "annee_academique", nullable = false, length = 20, unique = true)
    private String anneeAcademique;

    @OneToMany(mappedBy = "anneeAcademique", cascade = CascadeType.ALL)
    private List<DossierAnnuel> dossierAnnuels;

    // Constructors
    public AnneeAcademique() {
    }

    public AnneeAcademique(String anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    // Getters and Setters
    public Integer getAnneeAcademiqueId() {
        return anneeAcademiqueId;
    }

    public void setAnneeAcademiqueId(Integer anneeAcademiqueId) {
        this.anneeAcademiqueId = anneeAcademiqueId;
    }

    public String getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(String anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public List<DossierAnnuel> getDossierAnnuels() {
        return dossierAnnuels;
    }

    public void setDossierAnnuels(List<DossierAnnuel> dossierAnnuels) {
        this.dossierAnnuels = dossierAnnuels;
    }
}
