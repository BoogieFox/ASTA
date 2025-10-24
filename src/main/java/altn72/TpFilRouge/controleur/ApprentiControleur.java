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


    public ApprentiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping
    public String listerApprentis(Model model) {
        model.addAttribute("apprentis", apprentiService.getApprentis());
        return "apprentis/liste";
    }

    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("apprenti", new Apprenti());
        return "apprentis/formulaire";
    }

    @PostMapping
    public String creerApprenti(@ModelAttribute Apprenti apprenti) {
        apprentiService.ajouterApprenti(apprenti);
        return "redirect:/apprentis";
    }

    
}