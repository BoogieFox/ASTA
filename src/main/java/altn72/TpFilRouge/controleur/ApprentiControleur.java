package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {
    private final ApprentiService apprentiService;


    public ApprentiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    // Liste des apprentis avec données mockées
    @GetMapping
    public String listerApprentis() {
        return "apprentis/liste";
    }

    // Formulaire de création d'un nouvel apprenti
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation() {
        return "apprentis/formulaire";
    }

    // Formulaire de modification d'un apprenti (avec données mockées)
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Integer id) {
        return "apprentis/formulaire";
    }

    // Page dossier d'un apprenti (avec données mockées)
    @GetMapping("/{id}/dossier")
    public String afficherDossier(@PathVariable Integer id) {
        return "apprentis/dossier";
    }

    // Endpoint pour créer un apprenti (redirige vers la liste)
    @PostMapping
    public String creerApprenti(@ModelAttribute Apprenti apprenti) {
        apprentiService.ajouterApprenti(apprenti);
        return "redirect:/apprentis";
    }

    // Endpoint pour mettre à jour un apprenti (redirige vers la liste)
    @PostMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        // Pour l'instant, juste une redirection
        return "redirect:/apprentis";
    }
}