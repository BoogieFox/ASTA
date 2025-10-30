package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO pour la modification des informations d'un apprenti.
 * Inclut les donnees personnelles ainsi que les informations liees a la mission.
 */
@Setter
@Getter
public class ModifierApprentiDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @Size(max = 100, message = "Le prenom ne doit pas depasser 100 caracteres")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit etre valide")
    @Size(max = 100, message = "L'email ne doit pas depasser 100 caracteres")
    private String email;

    @NotBlank(message = "Le telephone est obligatoire")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Le telephone doit contenir entre 10 et 15 chiffres")
    private String telephone;

    @NotBlank(message = "La majeure est obligatoire")
    @Size(max = 100, message = "La majeure ne doit pas depasser 100 caracteres")
    private String majeure;

    @NotBlank(message = "Les mots-cles de mission sont obligatoires")
    @Size(max = 255, message = "Les mots-cles ne doivent pas depasser 255 caracteres")
    private String motsClesMission;

    @NotBlank(message = "Le metier cible est obligatoire")
    @Size(max = 100, message = "Le metier cible ne doit pas depasser 100 caracteres")
    private String metierCibleMission;

    @Size(max = 1024, message = "Les commentaires ne doivent pas depasser 1024 caracteres")
    private String commentairesMission;

    public ModifierApprentiDto() {}

    public ModifierApprentiDto(String nom, String prenom, String email, String telephone, String majeure) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.majeure = majeure;
    }
}
