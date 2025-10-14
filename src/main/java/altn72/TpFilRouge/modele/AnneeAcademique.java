package altn72.TpFilRouge.modele;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AnneeAcademique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annee_academique_id", nullable = false)
    private Integer anneeAcademiqueId;

    @Column(name = "annee_academique", nullable = false)
    private String anneeAcademique;
}
