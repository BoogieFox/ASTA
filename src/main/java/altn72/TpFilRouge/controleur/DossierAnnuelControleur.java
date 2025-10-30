package altn72.TpFilRouge.controleur;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import altn72.TpFilRouge.modele.dto.CreerRapportDto;
import altn72.TpFilRouge.modele.dto.CreerSoutenanceDto;
import altn72.TpFilRouge.modele.dto.CreerVisiteDto;
import altn72.TpFilRouge.service.RapportService;
import altn72.TpFilRouge.service.SoutenanceService;
import altn72.TpFilRouge.service.VisiteService;

@Tag(name = "Pages du dossier annuel", description = "Endpoints des vues Thymeleaf liées aux dossiers annuels (visites, soutenances, rapports)")
@Controller
@RequestMapping("/dossiers")
public class DossierAnnuelControleur {

    private static final String ATTR_SUCCESS = "successMessage";
    private static final String ATTR_ERROR = "errorMessage";
    private static final String REDIRECT_PREFIX = "redirect:/apprentis/";
    private static final String GERER_SUFFIX = "/gerer";

    private final RapportService rapportService;
    private final VisiteService visiteService;
    private final SoutenanceService soutenanceService;

    public DossierAnnuelControleur(RapportService rapportService,
                                   VisiteService visiteService,
                                   SoutenanceService soutenanceService) {
        this.rapportService = rapportService;
        this.visiteService = visiteService;
        this.soutenanceService = soutenanceService;
    }

    @Operation(
            summary = "Ajouter un rapport",
            description = "Traite l'ajout d'un nouveau rapport à un dossier annuel et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/rapport/ajouter")
    public String ajouterRapport(@Valid @ModelAttribute CreerRapportDto dto,
                                 BindingResult bindingResult,
                                 @RequestParam Integer apprentiId,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, formatValidationErrors(bindingResult, "RAPPORT"));
            return redirectToGerer(apprentiId);
        }

        try {
            rapportService.ajouterRapport(dto);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Rapport ajouté avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    @Operation(
            summary = "Modifier un rapport",
            description = "Traite la modification d'un rapport existant et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/rapport/{rapportId}/modifier")
    public String modifierRapport(@PathVariable Integer rapportId,
                                  @Valid @ModelAttribute CreerRapportDto dto,
                                  BindingResult bindingResult,
                                  @RequestParam Integer apprentiId,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, formatValidationErrors(bindingResult, "RAPPORT"));
            return redirectToGerer(apprentiId);
        }

        try {
            rapportService.modifierRapport(rapportId, dto);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Rapport modifié avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    @Operation(
            summary = "Supprimer un rapport",
            description = "Traite la suppression d'un rapport et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/rapport/{rapportId}/supprimer")
    public String supprimerRapport(@PathVariable Integer rapportId,
                                   @RequestParam Integer apprentiId,
                                   RedirectAttributes redirectAttributes) {
        try {
            rapportService.supprimerRapport(rapportId);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Rapport supprimé avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    // ========== VISITES ==========

    @Operation(
            summary = "Ajouter une visite",
            description = "Traite l'ajout d'une nouvelle visite à un dossier annuel et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/visite/ajouter")
    public String ajouterVisite(@Valid @ModelAttribute CreerVisiteDto dto,
                                BindingResult bindingResult,
                                @RequestParam Integer apprentiId,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, formatValidationErrors(bindingResult, "VISITE"));
            return redirectToGerer(apprentiId);
        }

        try {
            visiteService.ajouterVisite(dto);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Visite ajoutée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    @Operation(
            summary = "Modifier une visite",
            description = "Traite la modification d'une visite existante et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/visite/{visiteId}/modifier")
    public String modifierVisite(@PathVariable Integer visiteId,
                                 @Valid @ModelAttribute CreerVisiteDto dto,
                                 BindingResult bindingResult,
                                 @RequestParam Integer apprentiId,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, formatValidationErrors(bindingResult, "VISITE"));
            return redirectToGerer(apprentiId);
        }

        try {
            visiteService.modifierVisite(visiteId, dto);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Visite modifiée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    @Operation(
            summary = "Supprimer une visite",
            description = "Traite la suppression d'une visite et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/visite/{visiteId}/supprimer")
    public String supprimerVisite(@PathVariable Integer visiteId,
                                  @RequestParam Integer apprentiId,
                                  RedirectAttributes redirectAttributes) {
        try {
            visiteService.supprimerVisite(visiteId);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Visite supprimée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    // ========== SOUTENANCES ==========

    @Operation(
            summary = "Ajouter une soutenance",
            description = "Traite l'ajout d'une nouvelle soutenance à un dossier annuel et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/soutenance/ajouter")
    public String ajouterSoutenance(@Valid @ModelAttribute CreerSoutenanceDto dto,
                                    BindingResult bindingResult,
                                    @RequestParam Integer apprentiId,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, formatValidationErrors(bindingResult, "SOUTENANCE"));
            return redirectToGerer(apprentiId);
        }

        try {
            soutenanceService.ajouterSoutenance(dto);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Soutenance ajoutée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    @Operation(
            summary = "Modifier une soutenance",
            description = "Traite la modification d'une soutenance existante et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/soutenance/{soutenanceId}/modifier")
    public String modifierSoutenance(@PathVariable Integer soutenanceId,
                                     @Valid @ModelAttribute CreerSoutenanceDto dto,
                                     BindingResult bindingResult,
                                     @RequestParam Integer apprentiId,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, formatValidationErrors(bindingResult, "SOUTENANCE"));
            return redirectToGerer(apprentiId);
        }

        try {
            soutenanceService.modifierSoutenance(soutenanceId, dto);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Soutenance modifiée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    @Operation(
            summary = "Supprimer une soutenance",
            description = "Traite la suppression d'une soutenance et redirige vers la page de gestion de l'apprenti."
    )
    @PostMapping("/soutenance/{soutenanceId}/supprimer")
    public String supprimerSoutenance(@PathVariable Integer soutenanceId,
                                      @RequestParam Integer apprentiId,
                                      RedirectAttributes redirectAttributes) {
        try {
            soutenanceService.supprimerSoutenance(soutenanceId);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Soutenance supprimée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ATTR_ERROR, "❌ " + e.getMessage());
        }

        return redirectToGerer(apprentiId);
    }

    private String redirectToGerer(Integer apprentiId) {
        return REDIRECT_PREFIX + apprentiId + GERER_SUFFIX;
    }

    private String formatValidationErrors(BindingResult bindingResult, String prefix) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> {
            String fieldName = getFieldDisplayName(error.getField());
            errorMessage.append(fieldName).append(" : ").append(error.getDefaultMessage()).append("<br>");
        });
        return prefix + ":" + errorMessage;
    }

    private String getFieldDisplayName(String fieldName) {
        return switch (fieldName) {
            case "date" -> "Date";
            case "format" -> "Format";
            case "commentaires" -> "Commentaires";
            case "sujet" -> "Sujet";
            case "noteFinale" -> "Note finale";
            default -> fieldName;
        };
    }
}

