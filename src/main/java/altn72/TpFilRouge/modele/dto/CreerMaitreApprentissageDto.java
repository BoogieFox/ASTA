package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreerMaitreApprentissageDto {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, min = 2, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, min = 2, message = "Le prénom doit contenir entre 2 et 100 caractères")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email doit contenir au maximum 100 caractères")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Le téléphone doit contenir entre 10 et 15 chiffres")
    private String telephone;

    @NotBlank(message = "Le poste est obligatoire")
    @Size(max = 100, min = 2, message = "Le poste doit contenir entre 2 et 100 caractères")
    private String poste;

    @Size(max = 1024, message = "Les remarques doivent contenir au maximum 1024 caractères")
    private String remarques;

    public CreerMaitreApprentissageDto() {}

    public CreerMaitreApprentissageDto(String nom, String prenom, String email, String telephone, String poste, String remarques) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.poste = poste;
        this.remarques = remarques;
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

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }
}
