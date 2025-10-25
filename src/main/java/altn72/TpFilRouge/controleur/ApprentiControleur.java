package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {
    private final ApprentiService apprentiService;
    private final EntrepriseService entrepriseService;


    public ApprentiControleur(ApprentiService apprentiService, EntrepriseService entrepriseService) {
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
    }

    // ========== Endpoints REST ==========
    
    // REST endpoint - commenté car on utilise Thymeleaf
    /*
    @PostMapping("/nouveau")
    @ResponseBody
    public ResponseEntity<Apprenti> creerApprenti(@Valid @RequestBody CreerApprentiDto dto) {
        Apprenti apprenti = apprentiService.ajouterApprenti(dto);
        URI location = URI.create("/apprentis/" + apprenti.getApprentiId());
        return ResponseEntity.created(location).body(apprenti);
    }
    */

    // ========== Pages Thymeleaf ==========

    // Liste des apprentis
    @GetMapping
    public String listerApprentis(org.springframework.ui.Model model) {
        model.addAttribute("apprentis", apprentiService.getApprentis());
        return "apprentis/liste";
    }

    // Formulaire de création d'un nouvel apprenti
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(@RequestParam(required = false) String success,
                                             HttpSession session,
                                             Model model) {
        // Récupérer les données depuis la session si elles existent
        CreerApprentiDto apprenti = (CreerApprentiDto) session.getAttribute("apprentiData");
        
        if (apprenti == null) {
            // Si pas de données en session, créer un nouvel objet vide
            apprenti = new CreerApprentiDto();
        } else {
            // Nettoyer la session après récupération
            session.removeAttribute("apprentiData");
        }
        
        model.addAttribute("apprenti", apprenti);
        model.addAttribute("isCreation", true);
        model.addAttribute("entreprises", entrepriseService.getEntreprises());
        
        // Afficher un message de succès si une entreprise vient d'être créée
        if ("entreprise-created".equals(success)) {
            model.addAttribute("successMessage", "✅ Entreprise créée avec succès ! Vous pouvez maintenant la sélectionner dans la liste.");
        }
        
        return "apprentis/formulaire";
    }

    // Endpoint Thymeleaf pour créer un apprenti
    @PostMapping("/nouveau")
    public String creerApprenti(@Valid @ModelAttribute("apprenti") CreerApprentiDto dto, 
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isCreation", true);
            model.addAttribute("entreprises", entrepriseService.getEntreprises());
            return "apprentis/formulaire";
        }
        
        // Les exceptions (ApprentiDejaExistantException, RessourceIntrouvableException)
        // sont automatiquement gérées par le GlobalExceptionHandler
        apprentiService.ajouterApprenti(dto);
        return "redirect:/apprentis";
    }

    // Endpoint pour sauvegarder les données en session avant d'aller au formulaire d'entreprise
    @PostMapping("/sauvegarder-session")
    public String sauvegarderEnSession(@ModelAttribute CreerApprentiDto dto, HttpSession session) {
        session.setAttribute("apprentiData", dto);
        return "redirect:/entreprises/nouveau";
    }

    // Formulaire de modification d'un apprenti (avec données mockées)
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
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