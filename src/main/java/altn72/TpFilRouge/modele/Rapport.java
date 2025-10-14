package altn72.TpFilRouge.modele;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rapport_id", nullable = false)
    private Integer rapportId;

    @Column(name = "sujet", nullable = false)
    private String sujet;

    @Column(name = "note_finale", nullable = false, length = 24)
    private String noteFormat;

    @Column(name = "commentaires", nullable = false, length = 24)
    private String commentaires;
}
