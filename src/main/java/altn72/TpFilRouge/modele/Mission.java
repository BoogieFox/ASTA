package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "mission", schema = "base_asta")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id", nullable = false)
    private Integer missionId;

    @Column(name = "mots_cles", length = 255)
    private String motsCles;

    @Column(name = "metier_cible", length = 100)
    private String metierCible;

    @Column(name = "commentaires", length = 1024)
    private String commentaires;

    @OneToOne
    @JoinColumn(name = "apprenti_id", nullable = false, unique = true)
    private Apprenti apprenti;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id", nullable = false)
    private AnneeAcademique anneeAcademique;

    // Constructors
    public Mission() {
    }

    public Mission(String motsCles, String metierCible, Apprenti apprenti, AnneeAcademique anneeAcademique) {
        this.motsCles = motsCles;
        this.metierCible = metierCible;
        this.apprenti = apprenti;
        this.anneeAcademique = anneeAcademique;
    }

    // Getters and Setters
    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(String motsCles) {
        this.motsCles = motsCles;
    }

    public String getMetierCible() {
        return metierCible;
    }

    public void setMetierCible(String metierCible) {
        this.metierCible = metierCible;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
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
}
