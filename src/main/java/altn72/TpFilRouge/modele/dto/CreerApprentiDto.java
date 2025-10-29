package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO pour la création d'un apprenti.
 * Contient toutes les informations nécessaires pour créer un nouvel apprenti,
 * y compris les identifiants optionnels de l'entreprise et du maître d'apprentissage.
 */
public class CreerApprentiDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** Nom de famille de l'apprenti */
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    /** Prénom de l'apprenti */
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String prenom;

    /** Adresse email de l'apprenti (doit être unique) */
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    private String email;

    /** Numéro de téléphone de l'apprenti (10 à 15 chiffres) */
    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Le téléphone doit contenir entre 10 et 15 chiffres")
    private String telephone;

    /** Majeure ou spécialisation de l'apprenti */
    @NotBlank(message = "La majeure est obligatoire")
    @Size(max = 100, message = "La majeure ne doit pas dépasser 100 caractères")
    private String majeure;

    /** Identifiant de l'entreprise (optionnel) */
    private Integer entrepriseId;
    
    /** Identifiant du maître d'apprentissage (optionnel) */
    private Integer maitreApprentissageId;

    public CreerApprentiDto() {}

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
