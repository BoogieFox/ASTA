package altn72.TpFilRouge.modele;

import jakarta.persistence.*;

import java.util.Date;

public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visite_id", nullable = false)
    private Integer visiteId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "format", nullable = false, length = 24)
    private String Format;

    @Column(name = "commentaires", nullable = false, length = 100)
    private String Commentaires;

    @OneToOne
    @JoinColumn(name = "apprenti_id")
    private Apprenti apprenti;
}
