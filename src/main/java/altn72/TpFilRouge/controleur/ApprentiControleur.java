package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {
    private final ApprentiService apprentiService;


    public ApprentiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    // ========== Endpoints REST ==========
    
    @PostMapping("/nouveau")
    @ResponseBody
    public ResponseEntity<Apprenti> creerApprenti(@Valid @RequestBody CreerApprentiDto dto) {
        Apprenti apprenti = apprentiService.ajouterApprenti(dto);
        URI location = URI.create("/apprentis/" + apprenti.getApprentiId());
        return ResponseEntity.created(location).body(apprenti);
    }

    // ========== Pages Thymeleaf ==========

    // Liste des apprentis avec données mockées
    @GetMapping
    public String listerApprentis() {
        return "apprentis/liste";
    }

    // Formulaire de création d'un nouvel apprenti
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(org.springframework.ui.Model model) {
        model.addAttribute("isCreation", true);
        return "apprentis/formulaire";
    }

    // Formulaire de modification d'un apprenti (avec données mockées)
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Integer id, org.springframework.ui.Model model) {
        model.addAttribute("isCreation", false);
        return "apprentis/formulaire";
    }

    // Page dossier d'un apprenti (avec données mockées)
    @GetMapping("/{id}/dossier")
    public String afficherDossier(@PathVariable Integer id) {
        return "apprentis/dossier";
    }



    // Endpoint pour mettre à jour un apprenti (redirige vers la liste)
    @PostMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        // Pour l'instant, juste une redirection
        return "redirect:/apprentis";
    }
}