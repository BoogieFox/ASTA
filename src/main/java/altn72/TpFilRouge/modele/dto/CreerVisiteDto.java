package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CreerVisiteDto {

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotBlank(message = "Le format est obligatoire")
    @Size(max = 50, message = "Le format doit contenir au maximum 50 caractères")
    private String format;

    @Size(max = 1024, message = "Les commentaires doivent contenir au maximum 1024 caractères")
    private String commentaires;

    private Integer dossierAnnuelId;

    public CreerVisiteDto() {}

    public CreerVisiteDto(LocalDate date, String format, String commentaires, Integer dossierAnnuelId) {
        this.date = date;
        this.format = format;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

