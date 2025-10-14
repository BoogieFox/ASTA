package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

import java.util.Date;

public class Soutenance {
    @Id
    @OneToOne
    @JoinColumn(name = "apprenti_id")
    private Apprenti apprenti;

    @Id
    @OneToOne
    @JoinColumn(name = "annee_academique_id")
    private String anneeAcademique;

    @Column(name = "note_finale", nullable = false, length = 24)
    private String noteFormat;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "commentaires", nullable = false, length = 24)
    private String commentaires;

}
