package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CreerSoutenanceDto {

    // Getters and Setters
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

}

