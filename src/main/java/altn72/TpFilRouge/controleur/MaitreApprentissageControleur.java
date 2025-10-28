package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.exception.MaitreApprentissageDejaExistantException;
import altn72.TpFilRouge.modele.MaitreApprentissage;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.modele.dto.CreerMaitreApprentissageDto;
import altn72.TpFilRouge.service.MaitreApprentissageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/maitre-apprentissage")
public class MaitreApprentissageControleur {

    private final ModelMapper modelMapper;
    private final MaitreApprentissageService maitreApprentissageService;

    public MaitreApprentissageControleur(ModelMapper modelMapper, MaitreApprentissageService maitreApprentissageService) {
        this.modelMapper = modelMapper;
        this.maitreApprentissageService = maitreApprentissageService;
    }

    // ========== Pages Thymeleaf ==========

    // Formulaire de création d'un nouveau maître d'apprentissage
    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(@RequestParam(required = false) String returnTo,
                                            HttpSession session,
                                            Model model) {
        // Récupérer les données d'apprenti depuis la session
        CreerApprentiDto apprentiData = (CreerApprentiDto) session.getAttribute("apprentiData");
        if (apprentiData == null) {
            apprentiData = new CreerApprentiDto();
        }

        model.addAttribute("maitreApprentissage", new CreerMaitreApprentissageDto());
        model.addAttribute("apprentiData", apprentiData);
        model.addAttribute("returnTo", returnTo);
        return "maitresApprentissage/formulaire";
    }

    // Endpoint Thymeleaf pour créer un maître d'apprentissage
    @PostMapping("/nouveau")
    public String creerMaitreApprentissage(@Valid @ModelAttribute("maitreApprentissage") CreerMaitreApprentissageDto dto,
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
            return "maitresApprentissage/formulaire";
        }

        try {
            MaitreApprentissage maitreApprentissage = modelMapper.map(dto, MaitreApprentissage.class);
            maitreApprentissageService.ajouterMaitreApprentissage(maitreApprentissage);

            // Ne pas nettoyer la session ici - elle sera nettoyée par ApprentiControleur
            // après avoir récupéré les données
            // session.removeAttribute("apprentiData"); // ← SUPPRIMÉ

            // Rediriger vers la page appropriée
            if (returnTo != null && !returnTo.isEmpty()) {
                return "redirect:/" + returnTo + "?success=maitre-created";
            } else {
                return "redirect:/apprentis/nouveau?success=maitre-created";
            }
        } catch (MaitreApprentissageDejaExistantException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("apprentiData", apprentiData);
            model.addAttribute("returnTo", returnTo);
            return "maitresApprentissage/formulaire";
        }
    }
}