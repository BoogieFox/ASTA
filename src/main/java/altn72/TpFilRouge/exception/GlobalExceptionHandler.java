package altn72.TpFilRouge.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Gestionnaire global des exceptions pour les contrôleurs Thymeleaf.
 * Utilise @ControllerAdvice pour intercepter les exceptions et les gérer de manière centralisée.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère l'exception quand une ressource demandée n'existe pas.
     */
    @ExceptionHandler(RessourceIntrouvableException.class)
    public String handleRessourceIntrouvableException(RessourceIntrouvableException ex, Model model) {
        model.addAttribute("errorTitle", "Ressource introuvable");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/custom-error";
    }

    /**
     * Gère l'exception quand une page ou ressource statique n'est pas trouvée.
     * Redirige vers la page d'accueil des apprentis.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Page introuvable");
        model.addAttribute("errorMessage", "La page que vous recherchez n'existe pas ou a été déplacée.");
        model.addAttribute("errorCode", "404");
        model.addAttribute("suggestedAction", "Retourner à l'accueil");
        model.addAttribute("homeUrl", "/apprentis");
        return "error/custom-error";
    }

    /**
     * Gère toutes les autres exceptions non prévues.
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Erreur interne");
        model.addAttribute("errorMessage", "Une erreur inattendue s'est produite. Veuillez réessayer plus tard.");
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorDetails", ex.getMessage());
        return "error/custom-error";
    }
}