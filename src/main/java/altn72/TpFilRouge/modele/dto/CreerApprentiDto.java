package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO pour la creation d'un apprenti.
 * Contient toutes les informations necessaires pour creer un nouvel apprenti,
 * y compris les identifiants optionnels de l'entreprise et du maitre d'apprentissage.
 */
public class CreerApprentiDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nom de famille de l'apprenti */
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
    private String nom;

    /** Prenom de l'apprenti */
    @NotBlank(message = "Le prenom est obligatoire")
    @Size(max = 100, message = "Le prenom ne doit pas depasser 100 caracteres")
    private String prenom;

    /** Adresse email de l'apprenti (doit etre unique) */
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    @Size(max = 100, message = "L'email ne doit pas depasser 100 caracteres")
    private String email;

    /** Numero de telephone de l'apprenti (10 a 15 chiffres) */
    @NotBlank(message = "Le telephone est obligatoire")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Le telephone doit contenir entre 10 et 15 chiffres")
    private String telephone;

    /** Majeure ou specialisation de l'apprenti */
    @NotBlank(message = "La majeure est obligatoire")
    @Size(max = 100, message = "La majeure ne doit pas depasser 100 caracteres")
    private String majeure;

    /** Mots-cles associes a la mission */
    @NotBlank(message = "Les mots-cles de mission sont obligatoires")
    @Size(max = 255, message = "Les mots-cles ne doivent pas depasser 255 caracteres")
    private String motsClesMission;

    /** Metier vise par la mission */
    @NotBlank(message = "Le metier cible est obligatoire")
    @Size(max = 100, message = "Le metier cible ne doit pas depasser 100 caracteres")
    private String metierCibleMission;

    /** Commentaires autour de la mission */
    @Size(max = 1024, message = "Les commentaires ne doivent pas depasser 1024 caracteres")
    private String commentairesMission;

    /** Identifiant de l'entreprise (optionnel) */
    private Integer entrepriseId;

    /** Identifiant du maitre d'apprentissage (optionnel) */
    private Integer maitreApprentissageId;

    public CreerApprentiDto() {
        // Constructeur par defaut
    }

    public CreerApprentiDto(String nom, String prenom, String email, String telephone, String majeure) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.majeure = majeure;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMajeure() {
        return majeure;
    }

    public void setMajeure(String majeure) {
        this.majeure = majeure;
    }

    public String getMotsClesMission() {
        return motsClesMission;
    }

    public void setMotsClesMission(String motsClesMission) {
        this.motsClesMission = motsClesMission;
    }

    public String getMetierCibleMission() {
        return metierCibleMission;
    }

    public void setMetierCibleMission(String metierCibleMission) {
        this.metierCibleMission = metierCibleMission;
    }

    public String getCommentairesMission() {
        return commentairesMission;
    }

    public void setCommentairesMission(String commentairesMission) {
        this.commentairesMission = commentairesMission;
    }

    public Integer getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Integer entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public Integer getMaitreApprentissageId() {
        return maitreApprentissageId;
    }

    public void setMaitreApprentissageId(Integer maitreApprentissageId) {
        this.maitreApprentissageId = maitreApprentissageId;
    }
}
