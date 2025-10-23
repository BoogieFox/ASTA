package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {
    private final ApprentiService apprentiService;
    private final EntrepriseService entrepriseService;
    private final MaitreApprentissageService maitreApprentissageService;
    private final AnneeAcademiqueService anneeAcademiqueService;

    public ApprentiControleur(ApprentiService apprentiService,
                             EntrepriseService entrepriseService,
                             MaitreApprentissageService maitreApprentissageService,
                             AnneeAcademiqueService anneeAcademiqueService) {
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
        this.maitreApprentissageService = maitreApprentissageService;
        this.anneeAcademiqueService = anneeAcademiqueService;
    }    @GetMapping
    public String listerApprentis(Model model) {
        model.addAttribute("apprentis", apprentiService.getApprentis());
        return "apprentis/liste";
    }

    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("apprenti", new Apprenti());
        model.addAttribute("entreprises", entrepriseService.getEntreprises());
        model.addAttribute("maitresApprentissage", maitreApprentissageService.getMaitresApprentissage());
        model.addAttribute("anneesAcademiques", anneeAcademiqueService.getAnneesAcademiques());
        return "apprentis/formulaire";
    }

    @PostMapping
    public String creerApprenti(@ModelAttribute Apprenti apprenti) {
        apprentiService.ajouterApprenti(apprenti);
        return "redirect:/apprentis";
    }

    @GetMapping("/{id}")
    public String afficherApprenti(@PathVariable Integer id, Model model) {
        return apprentiService.getUnApprenti(id)
                .map(apprenti -> {
                    model.addAttribute("apprenti", apprenti);
                    // JPA charge automatiquement les relations grâce aux getters
                    model.addAttribute("missions", apprenti.getMissions());
                    model.addAttribute("visites", apprenti.getVisites());
                    model.addAttribute("rapports", apprenti.getRapports());
                    model.addAttribute("soutenances", apprenti.getSoutenances());
                    return "apprentis/detail";
                })
                .orElse("redirect:/apprentis");
    }

    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
        return apprentiService.getUnApprenti(id)
                .map(apprenti -> {
                    model.addAttribute("apprenti", apprenti);
                    model.addAttribute("entreprises", entrepriseService.getEntreprises());
                    model.addAttribute("maitresApprentissage", maitreApprentissageService.getMaitresApprentissage());
                    model.addAttribute("anneesAcademiques", anneeAcademiqueService.getAnneesAcademiques());
                    return "apprentis/formulaire";
                })
                .orElse("redirect:/apprentis");
    }

    @PostMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        apprentiService.modifierApprenti(id, apprenti);
        return "redirect:/apprentis";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimerApprenti(@PathVariable Integer id) {
        apprentiService.supprimerApprenti(id);
        return "redirect:/apprentis";
    }

    // Gestion des missions
    @PostMapping("/{id}/missions")
    public String ajouterMission(@PathVariable Integer id, @ModelAttribute altn72.TpFilRouge.modele.Mission mission) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            mission.setApprenti(apprenti);
            if (apprenti.getAnneeAcademique() != null) {
                mission.setAnneeAcademique(apprenti.getAnneeAcademique());
            }
            apprenti.getMissions().add(mission);
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    @PostMapping("/{id}/missions/{missionId}/supprimer")
    public String supprimerMission(@PathVariable Integer id, @PathVariable Integer missionId) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            apprenti.getMissions().removeIf(m -> m.getMissionId().equals(missionId));
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    // Gestion des visites
    @PostMapping("/{id}/visites")
    public String ajouterVisite(@PathVariable Integer id, @ModelAttribute altn72.TpFilRouge.modele.Visite visite) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            visite.setApprenti(apprenti);
            if (apprenti.getAnneeAcademique() != null) {
                visite.setAnneeAcademique(apprenti.getAnneeAcademique());
            }
            apprenti.getVisites().add(visite);
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    @PostMapping("/{id}/visites/{visiteId}/supprimer")
    public String supprimerVisite(@PathVariable Integer id, @PathVariable Integer visiteId) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            apprenti.getVisites().removeIf(v -> v.getVisiteId().equals(visiteId));
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    // Gestion des rapports
    @PostMapping("/{id}/rapports")
    public String ajouterRapport(@PathVariable Integer id, @ModelAttribute altn72.TpFilRouge.modele.Rapport rapport) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            rapport.setApprenti(apprenti);
            apprenti.getRapports().add(rapport);
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    @PostMapping("/{id}/rapports/{rapportId}/supprimer")
    public String supprimerRapport(@PathVariable Integer id, @PathVariable Integer rapportId) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            apprenti.getRapports().removeIf(r -> r.getRapportId().equals(rapportId));
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    // Gestion des soutenances
    @PostMapping("/{id}/soutenances")
    public String ajouterSoutenance(@PathVariable Integer id, @ModelAttribute altn72.TpFilRouge.modele.Soutenance soutenance) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            soutenance.setApprenti(apprenti);
            if (apprenti.getAnneeAcademique() != null) {
                soutenance.setAnneeAcademique(apprenti.getAnneeAcademique());
            }
            apprenti.getSoutenances().add(soutenance);
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }

    @PostMapping("/{id}/soutenances/{soutenanceId}/supprimer")
    public String supprimerSoutenance(@PathVariable Integer id, @PathVariable Integer soutenanceId) {
        apprentiService.getUnApprenti(id).ifPresent(apprenti -> {
            apprenti.getSoutenances().removeIf(s -> s.getSoutenanceId().equals(soutenanceId));
            apprentiService.ajouterApprenti(apprenti);
        });
        return "redirect:/apprentis/" + id;
    }
}