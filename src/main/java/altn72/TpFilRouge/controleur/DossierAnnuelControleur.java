package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.dto.CreerRapportDto;
import altn72.TpFilRouge.modele.dto.CreerSoutenanceDto;
import altn72.TpFilRouge.modele.dto.CreerVisiteDto;
import altn72.TpFilRouge.service.RapportService;
import altn72.TpFilRouge.service.SoutenanceService;
import altn72.TpFilRouge.service.VisiteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dossiers")
public class DossierAnnuelControleur {

    private final RapportService rapportService;
    private final VisiteService visiteService;
    private final SoutenanceService soutenanceService;

    public DossierAnnuelControleur(RapportService rapportService, VisiteService visiteService, SoutenanceService soutenanceService) {
        this.rapportService = rapportService;
        this.visiteService = visiteService;
        this.soutenanceService = soutenanceService;
    }

    // ========== RAPPORTS ==========

    @PostMapping("/rapport/ajouter")
    public String ajouterRapport(@Valid @ModelAttribute CreerRapportDto dto,
                                 BindingResult bindingResult,
                                 @RequestParam Integer apprentiId,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Erreur de validation : vérifiez les champs du rapport");
            return "redirect:/apprentis/" + apprentiId + "/gerer";
        }

        try {
            rapportService.ajouterRapport(dto);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Rapport ajouté avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    @PostMapping("/rapport/{rapportId}/modifier")
    public String modifierRapport(@PathVariable Integer rapportId,
                                  @Valid @ModelAttribute CreerRapportDto dto,
                                  BindingResult bindingResult,
                                  @RequestParam Integer apprentiId,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Erreur de validation : vérifiez les champs du rapport");
            return "redirect:/apprentis/" + apprentiId + "/gerer";
        }

        try {
            rapportService.modifierRapport(rapportId, dto);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Rapport modifié avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    @PostMapping("/rapport/{rapportId}/supprimer")
    public String supprimerRapport(@PathVariable Integer rapportId,
                                   @RequestParam Integer apprentiId,
                                   RedirectAttributes redirectAttributes) {
        try {
            rapportService.supprimerRapport(rapportId);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Rapport supprimé avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    // ========== VISITES ==========

    @PostMapping("/visite/ajouter")
    public String ajouterVisite(@Valid @ModelAttribute CreerVisiteDto dto,
                                BindingResult bindingResult,
                                @RequestParam Integer apprentiId,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Erreur de validation : vérifiez les champs de la visite");
            return "redirect:/apprentis/" + apprentiId + "/gerer";
        }

        try {
            visiteService.ajouterVisite(dto);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Visite ajoutée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    @PostMapping("/visite/{visiteId}/modifier")
    public String modifierVisite(@PathVariable Integer visiteId,
                                 @Valid @ModelAttribute CreerVisiteDto dto,
                                 BindingResult bindingResult,
                                 @RequestParam Integer apprentiId,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Erreur de validation : vérifiez les champs de la visite");
            return "redirect:/apprentis/" + apprentiId + "/gerer";
        }

        try {
            visiteService.modifierVisite(visiteId, dto);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Visite modifiée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    @PostMapping("/visite/{visiteId}/supprimer")
    public String supprimerVisite(@PathVariable Integer visiteId,
                                  @RequestParam Integer apprentiId,
                                  RedirectAttributes redirectAttributes) {
        try {
            visiteService.supprimerVisite(visiteId);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Visite supprimée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    // ========== SOUTENANCES ==========

    @PostMapping("/soutenance/ajouter")
    public String ajouterSoutenance(@Valid @ModelAttribute CreerSoutenanceDto dto,
                                    BindingResult bindingResult,
                                    @RequestParam Integer apprentiId,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Erreur de validation : vérifiez les champs de la soutenance");
            return "redirect:/apprentis/" + apprentiId + "/gerer";
        }

        try {
            soutenanceService.ajouterSoutenance(dto);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Soutenance ajoutée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    @PostMapping("/soutenance/{soutenanceId}/modifier")
    public String modifierSoutenance(@PathVariable Integer soutenanceId,
                                     @Valid @ModelAttribute CreerSoutenanceDto dto,
                                     BindingResult bindingResult,
                                     @RequestParam Integer apprentiId,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Erreur de validation : vérifiez les champs de la soutenance");
            return "redirect:/apprentis/" + apprentiId + "/gerer";
        }

        try {
            soutenanceService.modifierSoutenance(soutenanceId, dto);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Soutenance modifiée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }

    @PostMapping("/soutenance/{soutenanceId}/supprimer")
    public String supprimerSoutenance(@PathVariable Integer soutenanceId,
                                      @RequestParam Integer apprentiId,
                                      RedirectAttributes redirectAttributes) {
        try {
            soutenanceService.supprimerSoutenance(soutenanceId);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Soutenance supprimée avec succès !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return "redirect:/apprentis/" + apprentiId + "/gerer";
    }
}

