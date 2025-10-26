package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.modele.dto.ModifierApprentiDto;
import altn72.TpFilRouge.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {
    private final ApprentiService apprentiService;
    private final EntrepriseService entrepriseService;
    private final MaitreApprentissageService maitreApprentissageService;
    private final DossierAnnuelService dossierAnnuelService;


    public ApprentiControleur(ApprentiService apprentiService, 
                             EntrepriseService entrepriseService,
                             MaitreApprentissageService maitreApprentissageService,
                             DossierAnnuelService dossierAnnuelService) {
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
        this.maitreApprentissageService = maitreApprentissageService;
        this.dossierAnnuelService = dossierAnnuelService;
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
        model.addAttribute("maitresApprentissage", maitreApprentissageService.getMaitresApprentissage());

        // Afficher un message de succès si une entreprise vient d'être créée
        if ("entreprise-created".equals(success)) {
            model.addAttribute("successMessage", "✅ Entreprise créée avec succès ! Vous pouvez maintenant la sélectionner dans la liste.");
        }
        
        // Afficher un message de succès si un maître d'apprentissage vient d'être créé
        if ("maitre-created".equals(success)) {
            model.addAttribute("successMessage", "✅ Maître d'apprentissage créé avec succès ! Vous pouvez maintenant le sélectionner dans la liste.");
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
            model.addAttribute("maitresApprentissage", maitreApprentissageService.getMaitresApprentissage());
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

    // Endpoint pour sauvegarder les données en session avant d'aller au formulaire de maître d'apprentissage
    @PostMapping("/sauvegarder-session-maitre")
    public String sauvegarderEnSessionMaitre(@ModelAttribute CreerApprentiDto dto, HttpSession session) {
        session.setAttribute("apprentiData", dto);
        return "redirect:/maitre-apprentissage/nouveau";
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

    // Page pour gérer un apprenti (consultation + modification en une seule page)
    @GetMapping("/{id}/gerer")
    public String gererApprenti(@PathVariable Integer id, Model model) {
        Apprenti apprenti = apprentiService.getUnApprenti(id)
                .orElseThrow(() -> new altn72.TpFilRouge.exception.RessourceIntrouvableException("Apprenti", id));
        
        // Ajouter le DTO pour le binding si pas déjà présent (en cas d'erreur de validation)
        if (!model.containsAttribute("modifierApprentiDto")) {
            ModifierApprentiDto dto = new ModifierApprentiDto();
            dto.setNom(apprenti.getNom());
            dto.setPrenom(apprenti.getPrenom());
            dto.setEmail(apprenti.getEmail());
            dto.setTelephone(apprenti.getTelephone());
            dto.setMajeure(apprenti.getMajeure());
            model.addAttribute("modifierApprentiDto", dto);
        }
        
        model.addAttribute("dossiersAnnuels", dossierAnnuelService.getDossiersParApprenti(id));
        model.addAttribute("apprenti", apprenti);
        model.addAttribute("entreprises", entrepriseService.getEntreprises());
        model.addAttribute("maitresApprentissage", maitreApprentissageService.getMaitresApprentissage());
        
        return "apprentis/gerer";
    }

    // Endpoint pour mettre à jour un apprenti (redirige vers la liste)
    @PostMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        // Pour l'instant, juste une redirection
        return "redirect:/apprentis";
    }


    @PatchMapping("/{id}/informations")
    public String modifierInformationsPersonnelles(
            @PathVariable Integer id,
            @Valid @ModelAttribute("modifierApprentiDto") ModifierApprentiDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        // Gérer les erreurs de validation
        if (bindingResult.hasErrors()) {
            // Récupérer l'apprenti pour ré-afficher la page
            Apprenti apprenti = apprentiService.getUnApprenti(id)
                    .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", id));
            
            model.addAttribute("apprenti", apprenti);
            model.addAttribute("entreprises", entrepriseService.getEntreprises());
            model.addAttribute("maitresApprentissage", maitreApprentissageService.getMaitresApprentissage());
            model.addAttribute("validationError", true);
            
            return "apprentis/gerer";
        }
        
        // Les exceptions (ApprentiDejaExistantException, RessourceIntrouvableException)
        // sont automatiquement gérées par le GlobalExceptionHandler
        apprentiService.modifierApprenti(id, dto);
        
        // Message de succès
        redirectAttributes.addFlashAttribute("successMessage", "✅ Les informations ont été modifiées avec succès !");
        
        return "redirect:/apprentis/" + id + "/gerer";
    }
}