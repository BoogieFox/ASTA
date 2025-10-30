package altn72.TpFilRouge.controleur;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Page d'accueil", description = "Endpoint de redirection vers la page principale")
@Controller
public class AccueilControleur {

    @Operation(
            summary = "Page d'accueil",
            description = "Redirige automatiquement vers la liste des apprentis."
    )
    @GetMapping("/")
    public String accueil() {
        return "redirect:/apprentis";
    }
}
