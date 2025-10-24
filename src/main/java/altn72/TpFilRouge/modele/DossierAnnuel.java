package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "dossier_annuel", schema = "base_asta")
public class DossierAnnuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dossier_annuel_id", nullable = false)
    private Integer dossierAnnuelId;

    @ManyToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    private Apprenti apprenti;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id", nullable = false)
    private AnneeAcademique anneeAcademique;

    @OneToOne(mappedBy = "dossierAnnuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Rapport rapport;

    @OneToOne(mappedBy = "dossierAnnuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Visite visite;

    @OneToOne(mappedBy = "dossierAnnuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Soutenance soutenance;

    // Constructors
    public DossierAnnuel() {
    }

    public DossierAnnuel(Apprenti apprenti, AnneeAcademique anneeAcademique) {
        this.apprenti = apprenti;
        this.anneeAcademique = anneeAcademique;
    }

    // Getters and Setters
    public Integer getDossierAnnuelId() {
        return dossierAnnuelId;
    }

    public void setDossierAnnuelId(Integer dossierAnnuelId) {
        this.dossierAnnuelId = dossierAnnuelId;
    }

    public Apprenti getApprenti() {
        return apprenti;
    }

    public void setApprenti(Apprenti apprenti) {
        this.apprenti = apprenti;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Rapport getRapport() {
        return rapport;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
    }

    public Visite getVisite() {
        return visite;
    }

    public void setVisite(Visite visite) {
        this.visite = visite;
    }

    public Soutenance getSoutenance() {
        return soutenance;
    }

    public void setSoutenance(Soutenance soutenance) {
        this.soutenance = soutenance;
    }
}
