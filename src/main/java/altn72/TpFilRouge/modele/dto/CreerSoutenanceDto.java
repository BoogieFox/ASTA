package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CreerSoutenanceDto {

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @Min(value = 0, message = "La note doit être supérieure ou égale à 0")
    @Max(value = 20, message = "La note doit être inférieure ou égale à 20")
    private Double noteFinale;

    @Size(max = 1024, message = "Les commentaires doivent contenir au maximum 1024 caractères")
    private String commentaires;

    private Integer dossierAnnuelId;

    public CreerSoutenanceDto() {}

    public CreerSoutenanceDto(LocalDate date, Double noteFinale, String commentaires, Integer dossierAnnuelId) {
        this.date = date;
        this.noteFinale = noteFinale;
        this.commentaires = commentaires;
        this.dossierAnnuelId = dossierAnnuelId;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

