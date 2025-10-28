package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreerRapportDto {

    @NotBlank(message = "Le sujet est obligatoire")
    @Size(max = 255, min = 5, message = "Le sujet doit contenir entre 5 et 255 caractères")
    private String sujet;

    @Min(value = 0, message = "La note doit être supérieure ou égale à 0")
    @Max(value = 20, message = "La note doit être inférieure ou égale à 20")
    private Double noteFinale;

    @Size(max = 1024, message = "Les commentaires doivent contenir au maximum 1024 caractères")
    private String commentaires;

    private Integer dossierAnnuelId;

    public CreerRapportDto() {}

    public CreerRapportDto(String sujet, Double noteFinale, String commentaires, Integer dossierAnnuelId) {
        this.sujet = sujet;
        this.noteFinale = noteFinale;
        this.commentaires = commentaires;
        this.dossierAnnuelId = dossierAnnuelId;
    }

    // Getters and Setters
    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Double getNoteFinale() {
        return noteFinale;
    }

    public void setNoteFinale(Double noteFinale) {
        this.noteFinale = noteFinale;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Integer getDossierAnnuelId() {
        return dossierAnnuelId;
    }

    public void setDossierAnnuelId(Integer dossierAnnuelId) {
        this.dossierAnnuelId = dossierAnnuelId;
    }
}

