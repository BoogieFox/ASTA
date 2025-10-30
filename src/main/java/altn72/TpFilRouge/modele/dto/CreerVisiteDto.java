package altn72.TpFilRouge.modele.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CreerVisiteDto {

    // Getters and Setters
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

}

