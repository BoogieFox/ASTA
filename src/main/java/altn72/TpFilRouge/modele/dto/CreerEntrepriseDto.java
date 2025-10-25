package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreerEntrepriseDto {

    @NotBlank(message = "La raison sociale est obligatoire")
    @Size(max = 100, message = "La raison sociale ne doit pas dépasser 100 caractères")
    private String raisonSociale;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255, min = 10, message = "L'adresse doit contenir entre 10 et 255 caractères")
    private String adresse;

    private String informationsLocaux;

    public CreerEntrepriseDto() {}

    public CreerEntrepriseDto(String raisonSociale, String adresse, String informationsLocaux) {
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.informationsLocaux = informationsLocaux;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInformationsLocaux() {
        return informationsLocaux;
    }

    public void setInformationsLocaux(String informationsLocaux) {
        this.informationsLocaux = informationsLocaux;
    }
}