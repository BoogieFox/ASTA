package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.exception.ApprentiDejaExistantException;
import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Mission;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.modele.dto.ModifierApprentiDto;
import altn72.TpFilRouge.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Tag(name = "Pages des apprentis", description = "Endpoints des vues Thymeleaf liées à la gestion des apprentis")
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

    @Operation(
            summary = "Liste des apprentis",
            description = "Affiche la page Thymeleaf avec la liste des apprentis actifs et archivés. Supporte la recherche par mot-clé avec différents modes (tout, apprenti, entreprise, mission)."
    )
    @GetMapping
    public String listerApprentis(@RequestParam(required = false) String search,
                                  @RequestParam(required = false, defaultValue = "tout") String mode,
                                  org.springframework.ui.Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("apprentisActifs", apprentiService.searchApprentisActifs(search, mode));
            model.addAttribute("apprentisArchives", apprentiService.searchApprentisArchives(search, mode));
            model.addAttribute("searchTerm", search);
            model.addAttribute("searchMode", mode);
        } else {
            model.addAttribute("apprentisActifs", apprentiService.getApprentisActifs());
            model.addAttribute("apprentisArchives", apprentiService.getApprentisArchives());
            model.addAttribute("searchTerm", "");
            model.addAttribute("searchMode", mode);
        }
        return VIEW_LISTE;
    }

    @Operation(
            summary = "Formulaire de création d'un apprenti",
            description = "Affiche la page Thymeleaf du formulaire pour créer un nouvel apprenti."
    )
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(@RequestParam(required = false) String success,
                                             HttpSession session,
                                             Model model) {
        CreerApprentiDto apprenti = (CreerApprentiDto) session.getAttribute(SESSION_APPRENTI_DATA);

        if (apprenti == null) {
            apprenti = new CreerApprentiDto();
        } else {
            session.removeAttribute(SESSION_APPRENTI_DATA);
        }

        model.addAttribute(ATTR_APPRENTI, apprenti);
        model.addAttribute(ATTR_IS_CREATION, true);
        model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
        model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());

        if ("entreprise-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Entreprise créée avec succès ! Vous pouvez maintenant la sélectionner dans la liste.");
        }
        if ("maitre-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Maître d'apprentissage créé avec succès ! Vous pouvez maintenant le sélectionner dans la liste.");
        }

        return VIEW_FORMULAIRE;
    }

    @Operation(
            summary = "Créer un apprenti",
            description = "Traite le formulaire de création d'un apprenti et redirige vers la liste des apprentis."
    )
    @PostMapping("/nouveau")
    public String creerApprenti(@Valid @ModelAttribute(ATTR_APPRENTI) CreerApprentiDto dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ATTR_IS_CREATION, true);
            model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
            model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());
            return VIEW_FORMULAIRE;
        }

        try {
            apprentiService.ajouterApprenti(dto);
            return REDIRECT_APPRENTIS;
        } catch (ApprentiDejaExistantException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute(ATTR_APPRENTI, dto);
            return "redirect:/apprentis/nouveau";
        }
    }

    @Operation(
            summary = "Sauvegarder en session pour créer une entreprise",
            description = "Sauvegarde temporairement les données de l'apprenti en session et redirige vers le formulaire de création d'entreprise."
    )
    @PostMapping("/sauvegarder-session")
    public String sauvegarderEnSession(@ModelAttribute CreerApprentiDto dto, HttpSession session) {
        session.setAttribute(SESSION_APPRENTI_DATA, dto);
        return "redirect:/entreprises/nouveau";
    }

    @Operation(
            summary = "Sauvegarder en session pour créer un maître d'apprentissage",
            description = "Sauvegarde temporairement les données de l'apprenti en session et redirige vers le formulaire de création de maître d'apprentissage."
    )
    @PostMapping("/sauvegarder-session-maitre")
    public String sauvegarderEnSessionMaitre(@ModelAttribute CreerApprentiDto dto, HttpSession session) {
        session.setAttribute(SESSION_APPRENTI_DATA, dto);
        return "redirect:/maitre-apprentissage/nouveau";
    }

    @Operation(
            summary = "Formulaire de modification d'un apprenti",
            description = "Affiche la page Thymeleaf du formulaire pour modifier un apprenti existant."
    )
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable Integer id, Model model) {
        model.addAttribute(ATTR_IS_CREATION, false);
        return VIEW_FORMULAIRE;
    }

    @Operation(
            summary = "Page du dossier d'un apprenti",
            description = "Affiche la page Thymeleaf du dossier détaillé d'un apprenti."
    )
    @GetMapping("/{id}/dossier")
    public String afficherDossier(@PathVariable Integer id) {
        return "apprentis/dossier";
    }

    @Operation(
            summary = "Page de gestion d'un apprenti",
            description = "Affiche la page Thymeleaf pour gérer un apprenti (consultation et modification des informations)."
    )
    @GetMapping("/{id}/gerer")
    public String gererApprenti(@PathVariable Integer id,
                                @RequestParam(required = false) String success,
                                Model model) {
        Apprenti apprenti = apprentiService.getUnApprenti(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", id));

        if (!model.containsAttribute(ATTR_MODIFIER_DTO)) {
            ModifierApprentiDto dto = getModifierApprentiDto(apprenti);
            model.addAttribute(ATTR_MODIFIER_DTO, dto);
        }

        Optional<DossierAnnuel> dossierCourant = dossierAnnuelService.getDossierCourant(id);
        model.addAttribute(ATTR_DOSSIER_COURANT, dossierCourant.orElse(null));
        model.addAttribute(ATTR_APPRENTI, apprenti);
        model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
        model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());

        if ("entreprise-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Entreprise créée avec succès ! Vous pouvez maintenant la sélectionner dans la liste.");
        } else if ("maitre-created".equals(success)) {
            model.addAttribute(ATTR_SUCCESS, "✅ Maître d'apprentissage créé avec succès ! Vous pouvez maintenant le sélectionner dans la liste.");
        }

        return VIEW_GERER;
    }

    private static ModifierApprentiDto getModifierApprentiDto(Apprenti apprenti) {
        ModifierApprentiDto dto = new ModifierApprentiDto();
        dto.setNom(apprenti.getNom());
        dto.setPrenom(apprenti.getPrenom());
        dto.setEmail(apprenti.getEmail());
        dto.setTelephone(apprenti.getTelephone());
        dto.setMajeure(apprenti.getMajeure());


        Mission mission = apprenti.getMission();
        dto.setMotsClesMission(mission != null && mission.getMotsCles() != null ? mission.getMotsCles() : "");
        dto.setMetierCibleMission(mission != null && mission.getMetierCible() != null ? mission.getMetierCible() : "");
        dto.setCommentairesMission(mission != null && mission.getCommentaires() != null ? mission.getCommentaires() : "");
        return dto;
    }

    @Operation(
            summary = "Mettre à jour un apprenti",
            description = "Traite la mise à jour d'un apprenti et redirige vers la liste des apprentis."
    )
    @PostMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        // Pour l'instant, juste une redirection
        return REDIRECT_APPRENTIS;
    }

    @Operation(
            summary = "Modifier les informations personnelles d'un apprenti",
            description = "Traite la modification des informations personnelles d'un apprenti et redirige vers la page de gestion."
    )
    @PatchMapping("/{id}/informations")
    public String modifierInformationsPersonnelles(
            @PathVariable Integer id,
            @Valid @ModelAttribute("modifierApprentiDto") ModifierApprentiDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            Apprenti apprenti = apprentiService.getUnApprenti(id)
                    .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", id));

            model.addAttribute(ATTR_APPRENTI, apprenti);
            model.addAttribute(ATTR_ENTREPRISES, entrepriseService.getEntreprises());
            model.addAttribute(ATTR_MAITRES, maitreApprentissageService.getMaitresApprentissage());
            model.addAttribute(ATTR_VALIDATION_ERROR, true);

            return VIEW_GERER;
        }


        apprentiService.modifierApprenti(id, dto);
        redirectAttributes.addFlashAttribute(ATTR_SUCCESS, "✅ Les informations ont été modifiées avec succès !");
        return REDIRECT_GERER_PREFIX + id + GERER_SUFFIX;
    }

    @Operation(
            summary = "Modifier l'entreprise d'un apprenti",
            description = "Traite la modification de l'entreprise d'un apprenti et redirige vers la page de gestion."
    )
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

    @Operation(
            summary = "Modifier le maître d'apprentissage d'un apprenti",
            description = "Traite la modification du maître d'apprentissage d'un apprenti et redirige vers la page de gestion."
    )
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

    @Operation(
            summary = "Page de l'historique d'un apprenti",
            description = "Affiche la page Thymeleaf avec l'historique complet d'un apprenti archivé (tous ses dossiers annuels)."
    )
    @GetMapping("/{id}/historique")
    public String afficherHistorique(@PathVariable Integer id, Model model) {
        Apprenti apprenti = apprentiService.getUnApprenti(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", id));

        List<DossierAnnuel> dossiers = apprenti.getDossierAnnuels();
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

    @Operation(
            summary = "Commencer une nouvelle année académique",
            description = "Traite le passage à la nouvelle année académique (fait progresser tous les apprentis actifs d'une promotion) et redirige vers la liste des apprentis."
    )
    @PostMapping("/commencer-nouvelle-annee")
    public String commencerNouvelleAnnee(RedirectAttributes redirectAttributes) {
        try {
            int apprentisModifies = apprentiService.commencerNouvelleAnnee();
            if (apprentisModifies >1 ) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "✅ Nouvelle année commencée ! " + apprentisModifies + " apprentis ont été passés à la promotion suivante.");
            } else if (apprentisModifies ==1) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "✅ Nouvelle année commencée ! " + apprentisModifies + " apprenti a été passé à la promotion suivante.");
            } else {
                redirectAttributes.addFlashAttribute("successMessage",
                        "✅ Nouvelle année commencée ! Aucun apprenti n'a été modifié.");
            }
            redirectAttributes.addFlashAttribute("successMessage",
                    "✅ Nouvelle année commencée ! " + apprentisModifies + " apprentis ont été passés à la promotion suivante.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "❌ Erreur lors du passage de promotion : " + e.getMessage());
        }
        return REDIRECT_APPRENTIS;
    }
}
