package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.DossierAnnuel;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {
    private static final String ATTR_APPRENTI = "apprenti";
    private static final String ATTR_IS_CREATION = "isCreation";
    private static final String ATTR_ENTREPRISES = "entreprises";
    private static final String ATTR_MAITRES = "maitresApprentissage";
    private static final String ATTR_SUCCESS = "successMessage";
    private static final String ATTR_VALIDATION_ERROR = "validationError";
    private static final String ATTR_MODIFIER_DTO = "modifierApprentiDto";
    private static final String ATTR_DOSSIER_COURANT = "dossierCourant";
    private static final String SESSION_APPRENTI_DATA = "apprentiData";
    private static final String VIEW_LISTE = "apprentis/liste";
    private static final String VIEW_FORMULAIRE = "apprentis/formulaire";
    private static final String VIEW_GERER = "apprentis/gerer";
    private static final String REDIRECT_APPRENTIS = "redirect:/apprentis";
    private static final String REDIRECT_GERER_PREFIX = "redirect:/apprentis/";
    private static final String GERER_SUFFIX = "/gerer";

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

    // ========== Pages Thymeleaf ==========

    // Liste des apprentis
    @GetMapping
    public String listerApprentis(org.springframework.ui.Model model) {
        model.addAttribute("apprentisActifs", apprentiService.getApprentisActifs());
        model.addAttribute("apprentisArchives", apprentiService.getApprentisArchives());
        return VIEW_LISTE;
    }

    // Formulaire de création d'un nouvel apprenti
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(@RequestParam(required = false) String success,
                                             HttpSession session,
                                             Model model) {
        // Récupérer les données depuis la session si elles existent
        CreerApprentiDto apprenti = (CreerApprentiDto) session.getAttribute(SESSION_APPRENTI_DATA);
        
        if (apprenti == null) {
            // Si pas de données en session, créer un nouvel objet vide
            apprenti = new CreerApprentiDto();
        } else {
            // Nettoyer la session après récupération
            session.removeAttribute(SESSION_APPRENTI_DATA);
        }
        
        model.addAttribute(ATTR_APPRENTI, apprenti);
        model.addAttribute(ATTR_IS_CREATION, true);
        model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
        model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());

        // Afficher un message de succès si une entreprise vient d'être créée
        if ("entreprise-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Entreprise créée avec succès ! Vous pouvez maintenant la sélectionner dans la liste.");
        }
        
        // Afficher un message de succès si un maître d'apprentissage vient d'être créé
        if ("maitre-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Maître d'apprentissage créé avec succès ! Vous pouvez maintenant le sélectionner dans la liste.");
        }

        return VIEW_FORMULAIRE;
    }

    // Endpoint Thymeleaf pour créer un apprenti
    @PostMapping("/nouveau")
    public String creerApprenti(@Valid @ModelAttribute(ATTR_APPRENTI) CreerApprentiDto dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ATTR_IS_CREATION, true);
            model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
            model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());
            return VIEW_FORMULAIRE;
        }
        
        // Les exceptions (ApprentiDejaExistantException, RessourceIntrouvableException)
        // sont automatiquement gérées par le GlobalExceptionHandler
        apprentiService.ajouterApprenti(dto);
    return REDIRECT_APPRENTIS;
    }

    // Endpoint pour sauvegarder les données en session avant d'aller au formulaire d'entreprise
    @PostMapping("/sauvegarder-session")
    public String sauvegarderEnSession(@ModelAttribute CreerApprentiDto dto, HttpSession session) {
        session.setAttribute(SESSION_APPRENTI_DATA, dto);
        return "redirect:/entreprises/nouveau";
    }

    // Endpoint pour sauvegarder les données en session avant d'aller au formulaire de maître d'apprentissage
    @PostMapping("/sauvegarder-session-maitre")
    public String sauvegarderEnSessionMaitre(@ModelAttribute CreerApprentiDto dto, HttpSession session) {
        session.setAttribute(SESSION_APPRENTI_DATA, dto);
        return "redirect:/maitre-apprentissage/nouveau";
    }

    // Formulaire de modification d'un apprenti (avec données mockées)
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
        model.addAttribute(ATTR_IS_CREATION, false);
        return VIEW_FORMULAIRE;
    }

    // Page dossier d'un apprenti (avec données mockées)
    @GetMapping("/{id}/dossier")
    public String afficherDossier(@PathVariable Integer id) {
        return "apprentis/dossier";
    }

    // Page pour gérer un apprenti (consultation + modification en une seule page)
    @GetMapping("/{id}/gerer")
    public String gererApprenti(@PathVariable Integer id,
                               @RequestParam(required = false) String success,
                               Model model) {
    Apprenti apprenti = apprentiService.getUnApprenti(id)
        .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", id));
        
        // Ajouter le DTO pour le binding si pas déjà présent (en cas d'erreur de validation)
        if (!model.containsAttribute(ATTR_MODIFIER_DTO)) {
            ModifierApprentiDto dto = new ModifierApprentiDto();
            dto.setNom(apprenti.getNom());
            dto.setPrenom(apprenti.getPrenom());
            dto.setEmail(apprenti.getEmail());
            dto.setTelephone(apprenti.getTelephone());
            dto.setMajeure(apprenti.getMajeure());
            model.addAttribute(ATTR_MODIFIER_DTO, dto);
        }

    Optional<DossierAnnuel> dossierCourant = dossierAnnuelService.getDossierCourant(id);
        model.addAttribute(ATTR_DOSSIER_COURANT, dossierCourant.orElse(null));
        model.addAttribute(ATTR_APPRENTI, apprenti);
        model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
        model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());

        // Gérer les messages de succès
        if ("entreprise-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Entreprise créée avec succès ! Vous pouvez maintenant la sélectionner dans la liste.");
        } else if ("maitre-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Maître d'apprentissage créé avec succès ! Vous pouvez maintenant le sélectionner dans la liste.");
        }

        return VIEW_GERER;
    }

    // Endpoint pour mettre à jour un apprenti (redirige vers la liste)
    @PostMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        // Pour l'instant, juste une redirection
        return REDIRECT_APPRENTIS;
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
            
            model.addAttribute(ATTR_APPRENTI, apprenti);
            model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
            model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());
            model.addAttribute(ATTR_VALIDATION_ERROR, true);

            return VIEW_GERER;
        }
        
        // Les exceptions (ApprentiDejaExistantException, RessourceIntrouvableException)
        // sont automatiquement gérées par le GlobalExceptionHandler
        apprentiService.modifierApprenti(id, dto);
        
        // Message de succès
        redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Les informations ont été modifiées avec succès !");

        return REDIRECT_GERER_PREFIX + id + GERER_SUFFIX;
    }

    // Endpoint pour modifier l'entreprise d'un apprenti
    @PatchMapping("/{id}/entreprise")
    public String modifierEntreprise(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer entrepriseId,
            RedirectAttributes redirectAttributes) {

        try {
            apprentiService.modifierEntreprise(id, entrepriseId);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ L'entreprise a été modifiée avec succès !");
        } catch (RessourceIntrouvableException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return REDIRECT_GERER_PREFIX + id + GERER_SUFFIX;
    }

    // Endpoint pour modifier le maître d'apprentissage d'un apprenti
    @PatchMapping("/{id}/maitre-apprentissage")
    public String modifierMaitreApprentissage(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer maitreApprentissageId,
            RedirectAttributes redirectAttributes) {

        try {
            apprentiService.modifierMaitreApprentissage(id, maitreApprentissageId);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Le maître d'apprentissage a été modifié avec succès !");
        } catch (RessourceIntrouvableException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ " + e.getMessage());
        }

        return REDIRECT_GERER_PREFIX + id + GERER_SUFFIX;
    }

    // Endpoint pour afficher l'historique complet d'un apprenti archivé
    @GetMapping("/{id}/historique")
    public String afficherHistorique(@PathVariable Integer id, Model model) {
        Apprenti apprenti = apprentiService.getUnApprenti(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", id));

        // Récupérer tous les dossiers annuels
        List<DossierAnnuel> dossiers = apprenti.getDossierAnnuels();

        // Séparer les dossiers par promotion
        DossierAnnuel dossierL1 = dossiers.stream()
                .filter(d -> d.getPromotion() == altn72.TpFilRouge.modele.Promotion.L1)
                .findFirst()
                .orElse(null);

        DossierAnnuel dossierL2 = dossiers.stream()
                .filter(d -> d.getPromotion() == altn72.TpFilRouge.modele.Promotion.L2)
                .findFirst()
                .orElse(null);

        DossierAnnuel dossierL3 = dossiers.stream()
                .filter(d -> d.getPromotion() == altn72.TpFilRouge.modele.Promotion.L3)
                .findFirst()
                .orElse(null);

        model.addAttribute(ATTR_APPRENTI, apprenti);
        model.addAttribute("dossierL1", dossierL1);
        model.addAttribute("dossierL2", dossierL2);
        model.addAttribute("dossierL3", dossierL3);

        return "apprentis/historique";
    }

    // Endpoint pour commencer une nouvelle année
    @PostMapping("/commencer-nouvelle-annee")
    public String commencerNouvelleAnnee(RedirectAttributes redirectAttributes) {
        try {
            int apprentisModifies = apprentiService.commencerNouvelleAnnee();
            redirectAttributes.addFlashAttribute("successMessage", 
                "✅ Nouvelle année commencée ! " + apprentisModifies + " apprentis ont été passés à la promotion suivante.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Erreur lors du passage de promotion : " + e.getMessage());
        }
        
        return REDIRECT_APPRENTIS;
    }
}