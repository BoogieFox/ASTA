package altn72.TpFilRouge.modele;

import jakarta.persistence.*;
import java.util.List;

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

    // Getters et Setters
    public Integer getMaitreApprentissageId() { return tuteurId; }
    public void setMaitreApprentissageId(Integer maitreApprentissageId) { this.tuteurId = maitreApprentissageId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public List<Apprenti> getApprentis() { return apprentis; }
    public void setApprentis(List<Apprenti> apprentis) { this.apprentis = apprentis; }
}
