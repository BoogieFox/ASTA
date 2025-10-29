package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.exception.EntrepriseDejaExistanteException;
import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.modele.dto.CreerEntrepriseDto;
import altn72.TpFilRouge.service.EntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pages des entreprises", description = "Endpoints des vues Thymeleaf liées à la gestion des entreprises")
@Controller
@RequestMapping("/entreprises")
public class EntrepriseControleur {

    private final ModelMapper modelMapper;
    private final EntrepriseService entrepriseService;

    public EntrepriseControleur(ModelMapper modelMapper, EntrepriseService entrepriseService) {
        this.modelMapper = modelMapper;
        this.entrepriseService = entrepriseService;
    }

    // ========== Endpoints REST (commentés car on utilise Thymeleaf) ==========
    /*
    @PostMapping("/nouveau")
    public ResponseEntity<Entreprise> creerEntreprise(@Valid @RequestBody CreerEntrepriseDto dto) {
        Entreprise entreprise = modelMapper.map(dto, Entreprise.class);
        Entreprise created = entrepriseService.ajouterEntreprise(entreprise);
        URI location = URI.create("/api/entreprises/" + created.getEntrepriseId());
        return ResponseEntity.created(location).body(created);
    }
    */

    // ========== Pages Thymeleaf ==========

    @Operation(
        summary = "Formulaire de création d'une entreprise",
        description = "Affiche la page Thymeleaf du formulaire pour créer une nouvelle entreprise."
    )
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(@RequestParam(required = false) String returnTo,
                                            HttpSession session,
                                            Model model) {
        // Récupérer les données d'apprenti depuis la session
        CreerApprentiDto apprentiData = (CreerApprentiDto) session.getAttribute("apprentiData");
        if (apprentiData == null) {
            apprentiData = new CreerApprentiDto();
        }
        
        model.addAttribute("entreprise", new CreerEntrepriseDto());
        model.addAttribute("apprentiData", apprentiData);
        model.addAttribute("returnTo", returnTo);
        return "entreprises/formulaire";
    }

    @Operation(
        summary = "Créer une entreprise",
        description = "Traite le formulaire de création d'une entreprise et redirige vers la page d'origine avec un message de succès."
    )
    @PostMapping("/nouveau")
    public String creerEntreprise(@Valid @ModelAttribute("entreprise") CreerEntrepriseDto dto,
                                  BindingResult bindingResult,
                                  @RequestParam(required = false) String returnTo,
                                  HttpSession session,
                                  Model model) {
        // Récupérer les données d'apprenti depuis la session
        CreerApprentiDto apprentiData = (CreerApprentiDto) session.getAttribute("apprentiData");
        if (apprentiData == null) {
            apprentiData = new CreerApprentiDto();
        }
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("apprentiData", apprentiData);
            model.addAttribute("returnTo", returnTo);
            return "entreprises/formulaire";
        }

        try {
            Entreprise entreprise = modelMapper.map(dto, Entreprise.class);
            entrepriseService.ajouterEntreprise(entreprise);
            
            // Ne pas nettoyer la session ici - elle sera nettoyée par ApprentiControleur
            // après avoir récupéré les données
            // session.removeAttribute("apprentiData"); // ← SUPPRIMÉ
            
            // Rediriger vers la page appropriée
            if (returnTo != null && !returnTo.isEmpty()) {
                return "redirect:/" + returnTo + "?success=entreprise-created";
            } else {
                return "redirect:/apprentis/nouveau?success=entreprise-created";
            }
        } catch (EntrepriseDejaExistanteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("apprentiData", apprentiData);
            model.addAttribute("returnTo", returnTo);
            return "entreprises/formulaire";
        }
    }
}